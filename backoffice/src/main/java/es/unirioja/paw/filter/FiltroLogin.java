/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Jarein
 */
@WebFilter(urlPatterns = "/*")
public class FiltroLogin implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(true);

        if (session.getAttribute("usuario")!=null){
            chain.doFilter(request,response);
        }else{
            String uri=httpRequest.getRequestURI();
            if (uri.contains("/auth/login") || uri.contains("/auth/logout") || uri.contains("/assets/")) {
            chain.doFilter(request, response);
            return;
        }
            else{
                session.setAttribute("urlAnterior", uri);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            }
        }
    }
}
