<%-- 
    Document   : error.jsp
    Created on : 8 mar 2025, 18:51:11
    Author     : Jarein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/practica4.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h1>Ups</h1>
            <div class="img_error">
                <img src="<%= request.getContextPath()%>/assets/images/96938.jpg" alt="imagen de error">
            </div>
            <h3>Error <c:out default="Desconocido" value="${requestScope['jakarta.servlet.error.status_code']}"/></h3>
            <div>
                <strong>Excepción:</strong> 
                <c:out default="No se proporcionó excepción" value="${requestScope['jakarta.servlet.error.exception']}"/>
            </div>
            <div>
                <strong>Tipo de excepción:</strong> 
                <c:out default="Desconocido" value="${requestScope['jakarta.servlet.error.exception_type']}"/>
            </div>
            <div>
                <strong>URI:</strong> 
                <c:out default="Desconocida" value="${requestScope['jakarta.servlet.error.request_uri']}"/>
            </div>
            <div>
                <strong>Servlet:</strong> 
                <c:out default="Desconocido" value="${requestScope['jakarta.servlet.error.servlet_name']}"/>
            </div>
            <div>
                <strong>Mensaje:</strong> 
                <c:out default="Error de aplicación" value="${requestScope['jakarta.servlet.error.message']}"/>
            </div>
            <div class="boton-salir-container">
                <a href="<c:out value="${enlaceSalir}" />" class="boton-salir">Salir de aquí</a>
            </div>
        </main>

        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
