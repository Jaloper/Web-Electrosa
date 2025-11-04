/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.PedidoDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
import es.unirioja.paw.mysql.PedidoDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarein
 */
@WebServlet(name = "PedidoController", urlPatterns = {"/pedidos"})
public class PedidoController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private PedidoDAO pedidoDAO = new PedidoDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pedido> items;
        try {
            items = pedidoDAO.findAll();
        } catch (ExcepcionDeAplicacion ex) {
            throw new ServletException(ex);
        }
        logger.info("pedidos={} items", items == null ? "null" : items.size());
        request.setAttribute("pedidoCollection", items);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pedido-list.jsp");
        rd.forward(request, response);
    }

}
