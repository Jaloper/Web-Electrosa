<%-- 
    Document   : cliente-list
    Created on : 16 mar 2025, 7:03:52
    Author     : Jarein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="assets/css/practica3.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h2>Clientes</h2>
            <div class="Num">
                 <a>Elementos encontrados: </a><a>${clienteCollection.size()}</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Usuario</th>
                        <th>Nombre</th>
                        <th>Email</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="cliente" items="${clienteCollection}">
                        <tr>
                            <td><div class="codigo"><a href="cliente?clientId=${cliente.codigo}">${cliente.username}</a></div></td>
                            <td>${cliente.nombre}</td>
                            <td>${cliente.email}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
