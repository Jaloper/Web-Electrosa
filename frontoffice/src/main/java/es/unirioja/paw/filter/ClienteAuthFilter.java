/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarein
 */
@WebFilter(filterName = "FiltroUsuario", urlPatterns = {"/cliente/*", "/clientes/*"})
public class ClienteAuthFilter implements Filter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

       @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String contexto= request.getContextPath();
        String URLdestino = request.getRequestURI().substring(contexto.length());
        
        if (session != null && session.getAttribute("cliente") != null) {
            chain.doFilter(req, resp);
            return;
        }
        
        String queryString = request.getQueryString();
        if (queryString != null) {
            URLdestino += "?" + queryString;
        }
        
        session = request.getSession(true);
        logger.info("URLdestino= "+URLdestino);
        session.setAttribute("URLdestino", URLdestino);
        response.sendRedirect(contexto+"/auth/login");
    }
    
}
