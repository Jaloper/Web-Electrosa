/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import es.unirioja.paw.pagination.PageNumberPagination;
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
import es.unirioja.paw.dao.CatalogoDAO;

;

/**
 *
 * @author Jarein
 */
@WebServlet(name = "CatalogueController", urlPatterns = {"/catalogue"})
public class CatalogueController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogoDAO catalogoDao = new CatalogoDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 12;
        int currentPage = 1;

        String pageParam = request.getParameter("p");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        try {

            PageNumberPagination pagination = catalogoDao.buildPageNumberPagination(pageSize, currentPage);
            if (currentPage > pagination.getTotalPages()) {
                currentPage = pagination.getTotalPages();
            }
            List<Articulo> articulosPagina = catalogoDao.findByPage(currentPage, pageSize);
            request.setAttribute("articulos", articulosPagina);
            request.setAttribute("pagination", pagination);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/article-list.jsp");
            rd.forward(request, response);
        } catch (ExcepcionDeAplicacion ex) {
            throw new ServletException(ex);
        }
    }
}
