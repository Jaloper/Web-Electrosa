/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.UsuarioDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Usuario;
import es.unirioja.paw.mysql.UsuarioDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarein
 */
@WebServlet(name = "LoginController", urlPatterns = {"/auth/login"})
public class LoginController extends HttpServlet {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private UsuarioDAO usuarioDao = new UsuarioDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");

        if (usuario == null || usuario.isBlank() || contraseña == null || contraseña.isBlank()) {
            request.setAttribute("error", "Falta usuario o contraseña");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
            rd.forward(request, response);
            return;
        }
        try {
            if (esCorrecto(usuario, contraseña)) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", new Usuario(usuario));
                String urlAnterior = (String) session.getAttribute("urlAnterior");
                if (urlAnterior != null) {
                    session.removeAttribute("urlAnterior");
                    response.sendRedirect(urlAnterior);
                } else {
                    response.sendRedirect(request.getContextPath() + "/welcome.html");
                }
            } else {
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.setAttribute("usuario", usuario);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
                rd.forward(request, response);
            }
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al buscar usuario", usuario);
            request.setAttribute("enlaceSalir", "welcome.html");
            throw new ServletException(ex);
        }
    }

    private boolean esCorrecto(String username, String password) throws ExcepcionDeAplicacion {
            Usuario usuario = usuarioDao.findOneByCodigo(username);
            if (usuario == null) return false;
            String encodedPassword = encodeValue(password, "SHA-1");
            if (encodedPassword.equals(usuarioDao.getHashedPassword(usuario)) && usuarioDao.esAdmin(usuario)) return true;
        
        return false;
    }

    private String encodeValue(String value, String algorithm) {
        byte[] valueBytes = value.getBytes();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (Exception ex) {
            logger.error("Error getting MessageDigest instance", ex);
            return value;
        }
        messageDigest.reset();
        messageDigest.update(valueBytes);
        byte[] hashBytes = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : hashBytes) {
            if ((b & 0xFF) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString((b & 0xFF), 16));
        }
        return stringBuffer.toString();
    }
}

