/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarein
 */
@WebServlet(name = "ArticleImageController", urlPatterns = {"/articleImage"})
@MultipartConfig(
)
public class ArticleImageController extends HttpServlet {

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
        request.setAttribute("codigo", codigo);
        request.setAttribute("rutaImagen", a.getFoto());
        request.setAttribute("descripcion", a.getDescripcion());
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/article-image.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigo = request.getParameter("codigo");
        Part imagenPart = request.getPart("imagen");

        if (codigo == null || codigo.isBlank()) {
            logger.info("codigo null o vacío");
            response.sendRedirect("welcome.html");
            return;
        }

        if (imagenPart != null && imagenPart.getSize() > 0) {
            String submittedFileName = Paths.get(imagenPart.getSubmittedFileName())
                    .getFileName().toString();

            try {
                Articulo art = catalogoDao.findOneByCodigo(codigo);
                String carpeta = art.getTipo();

                String ruta = request.getServletContext()
                        .getRealPath("/assets/images/store");
                File carpetaDir = new File(ruta, carpeta);
                if (!carpetaDir.exists()) { //Si no existe la carpeta, creamos una nueva (se ha añadido un nuevo tipo a la tienda)
                    carpetaDir.mkdirs();
                }

                File destino = new File(carpetaDir, submittedFileName);
                try (InputStream in = imagenPart.getInputStream(); FileOutputStream out = new FileOutputStream(destino)) {
                    in.transferTo(out);
                }

                String nuevaRuta = carpeta + "/" + submittedFileName;
                art.setFoto(nuevaRuta);
                articuloDao.saveEntity(art);
                request.getSession().setAttribute("mensajeExito","Imagen actualizada correctamente");

            } catch (ExcepcionDeAplicacion ex) {
                logger.error("Error actualizando foto en BD para artículo {}", codigo, ex);
                request.setAttribute("enlaceSalir", "welcome.html");
                    throw new ServletException(ex);
            } catch (IOException ioe) {
                logger.error("Error guardando fichero de imagen para artículo {}", codigo, ioe);
                request.getSession().setAttribute("mensajeError",
                        "No se pudo guardar la imagen");
            }
        }
        response.sendRedirect(request.getContextPath() + "/articleImage?artId=" + URLEncoder.encode(codigo, "UTF-8"));
    }


}

