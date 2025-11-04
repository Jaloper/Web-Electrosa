/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarein
 */
@WebServlet(name = "ClientesController", urlPatterns = {"/clientes"})
public class ClientesController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClienteDAO clienteDAO = new ClienteDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> items;
        try {
            items = clienteDAO.findAll();
        } catch (ExcepcionDeAplicacion ex) {
            throw new ServletException(ex);
        }
        logger.info("clientes={} items", items == null ? "null" : items.size());
        request.setAttribute("clienteCollection", items);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cliente-list.jsp");
        rd.forward(request, response);
    }

}