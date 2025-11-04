<%-- 
    Document   : cliente-detail
    Created on : 16 mar 2025, 8:44:25
    Author     : Jarein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="assets/css/practica4.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h1>Detalles del cliente</h1>
            <div class="imagen-izquierda">
                <img src="<%= request.getContextPath()%>/assets/images/avatar/7294811.jpg" alt="Avatar">
            </div>

            <h3>${cliente.nombre}</h3>
            <div class="lines">
                <div>
                    <label>Código:</label> ${cliente.codigo}
                </div>
                <div>
                    <label>Usuario:</label> ${cliente.username}
                </div>
                <div>
                    <label>Email:</label> ${cliente.email}
                </div>
                <div>
                    <label>Dirección:</label> ${cliente.codigoPostal} ${cliente.ciudad} (${cliente.provincia})
                </div>
            </div>
        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>

