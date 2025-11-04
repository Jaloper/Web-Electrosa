package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ClienteDAO;
import es.unirioja.paw.model.Cliente;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ClienteDaoMySQL;
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

@WebServlet(name = "ClienteController", urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClienteDAO clienteDAO = new ClienteDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lectura y validación de la entrada
        String clientId = request.getParameter("clientId");
        if (clientId == null || clientId.isBlank()) {
            logger.info("clientId null or blank");
            response.sendRedirect("index.html");
            return;
        }
        // 2. Acceso al modelo
        Cliente a = null;
        try {
            a = clienteDAO.findOneByCodigo(clientId);
        } catch (ExcepcionDeAplicacion ex) {
//            throw new ServletException(ex);
            logger.error("Cliente {}", clientId);
            logger.error("Error al buscar articulo", ex);
        }

        if (a == null) {
            response.sendRedirect("error.jsp?msg="
                    + URLEncoder.encode(
                            "El cliente no existe", Charset.forName("UTF-8"))
            );
            return;
        }
        // 3. Preparación de datos para la vista
        request.setAttribute("cliente", a);
        // 4. Carga de la vista
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/client-detail.jsp");
        rd.forward(request, response);
    }

}
