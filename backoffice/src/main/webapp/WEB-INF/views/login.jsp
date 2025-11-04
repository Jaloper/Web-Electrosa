<%-- 
    Document   : login
    Created on : 19 mar 2025, 18:23:36
    Author     : Jarein
--%>

<%@ page import="java.util.List"%>
<%@ page import="es.unirioja.paw.model.Articulo"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/practica3.css">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/practica5.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <section class="card-layout">
                <div class="card-layout__item">
                    <h2>ELECTROSA</h2>
                    <img src="<%= request.getContextPath()%>/assets/images/logo-electrosa-retro.png" 
                         alt="Logo Electrosa" style="width: 100px; height: auto; margin-bottom: 15px;">
                    <form action="<%= request.getContextPath()%>/auth/login" method="POST">
                        <div style="margin-bottom: 10px;">
                            <label for="usuario">Usuario:</label>
                            <input type="text" id="usuario" name="usuario" required value="<%= request.getAttribute("usuario") != null ? request.getAttribute("usuario") : "" %>">
                        </div>

                        <div style="margin-bottom: 15px;">
                            <label for="contraseña">Contraseña:</label>
                            <input type="password" id="contraseña" name="contraseña" required>
                        </div>

                        <input type="submit" class="btn-editar" value="Acceder">

                        <% 
                        String error = (String) request.getAttribute("error");
                        if (error != null) { 
                        %>
                             <p class="mensaje-error"><%= error %></p>
                        <% } %>
                    </form>
                </div>
            </section>
        </main>

        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>