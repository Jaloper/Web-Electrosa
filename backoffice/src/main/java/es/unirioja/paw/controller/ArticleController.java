/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
  * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.dao.FabricanteDAO;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Jarein
 */
@WebServlet(name = "ArticleController", urlPatterns = {"/article"})
public class ArticleController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogoDAO catalogoDao = new CatalogoDaoMySQL();
    private ArticuloDAO articuloDao = new ArticuloDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigo = request.getParameter("artId");
        if (codigo == null || codigo.isBlank()) {
            logger.info("codigo null o vacío");
            response.sendRedirect("welcome.html");
            return;
        }

        Articulo a = null;
        try {
            a = catalogoDao.findOneByCodigo(codigo);
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al buscar articulo", codigo);
            request.setAttribute("enlaceSalir", "welcome.html");
            throw new ServletException(ex);
        }

        if (a == null) {
            request.setAttribute("enlaceSalir", "catalogo");
            response.sendError(404, "El artículo solicitado no existe");
            return;
        }
        //Cabecera referer para saber cual era el valor de anterior de la URL.
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isBlank() || !referer.contains("catalogue")) {
            referer = "catalogue";
        }
        request.setAttribute("volverUrl", referer);

        List<TipoArticulo> todosLostipos = new ArrayList<>();
        try {
            todosLostipos = ArticuloDAO.findTiposArticulos();
        } catch (ExcepcionDeAplicacion ex) {
            java.util.logging.Logger.getLogger(ArticleController.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<TipoArticulo> tipos = new ArrayList<>();
        for (TipoArticulo t : todosLostipos) {
            if (!t.getNombre().equals(a.getTipo())) {
                tipos.add(t);
            }
        }
        request.setAttribute("tipos", tipos);

        List<Fabricante> todosLosfabricantes = new ArrayList<>();
        try {
            todosLosfabricantes = FabricanteDAO.findAll();
        } catch (ExcepcionDeAplicacion ex) {
            java.util.logging.Logger.getLogger(ArticleController.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Fabricante> fabricantes = new ArrayList<>();
        for (Fabricante f : todosLosfabricantes) {
            if (!f.getNombre().equals(a.getFabricante())) {
                fabricantes.add(f);
            }
        }
        request.setAttribute("fabricantes", fabricantes);
        request.setAttribute("articulo", a);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/article-detail.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigo = request.getParameter("artId");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String tipo = request.getParameter("tipo");
        String fabricante = request.getParameter("fabricante");
        String precioStr = request.getParameter("precio");
        String foto = request.getParameter("foto");
        List<String> errores = new ArrayList<>();

        if (codigo == null || codigo.isBlank()) {
            errores.add("Campo código del artículo obligatorio.");
        }
        if (nombre == null || nombre.isBlank()) {
            errores.add("Campo nombre obligatorio.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            errores.add("Campo descripción obligatoria.");
        }
        if (tipo == null || tipo.isBlank()) {
            errores.add("Campo tipo obligatorio.");
        }
        if (fabricante == null || fabricante.isBlank()) {
            errores.add("Campo fabricante obligatorio.");
        }
        if (precioStr == null || precioStr.isBlank()) {
            errores.add("Campo precio obligatorio.");
        }

        double precio = 0.0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                errores.add("El precio no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.add("El precio debe ser un número válido.");
        }

        if (nombre.length() > 50) {
            errores.add("Campo 'Nombre' debe tener como máximo 50 caracteres.");
        }
        if (descripcion.length() > 200) {
            errores.add("Campo 'Descripción' debe tener como máximo 200 caracteres.");
        }

        if (!errores.isEmpty()) { //Si hay errores volver a la vista con esos datos
            request.setAttribute("errores", errores);
            request.setAttribute("articulo", new Articulo(codigo, nombre, precio, tipo, fabricante, foto, descripcion));
            doGet(request, response);
            return;
        }

        Articulo articulo = null;
        try {
            articulo = catalogoDao.findOneByCodigo(codigo);
            if (articulo == null) {
                request.setAttribute("enlaceSalir", "catalogo");
                // TODO: la URL se implementará luego
                response.sendError(404, "El artículo solicitado no existe");
                return;
            }
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error recuperando articulo", codigo);
            request.setAttribute("enlaceSalir", "welcome.html");
            throw new ServletException(ex);
        }

        articulo.setNombre(nombre);
        articulo.setDescripcion(descripcion);
        articulo.setTipo(tipo);
        articulo.setFabricante(fabricante);
        articulo.setPvp(precio);
        articulo.setFoto(foto);

        try {
            if (articuloDao.saveEntity(articulo)) {
                HttpSession session = request.getSession();
                session.setAttribute("mensajeExito", "Cambios realizados correctamente");
            } else {
                errores.add("No se pudo actualizar el artículo en la base de datos.");
            }
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al actualizar el articulo", codigo);
            request.setAttribute("enlaceSalir", "welcome.html");
            throw new ServletException(ex);
        }
        doGet(request, response);
    }

}
