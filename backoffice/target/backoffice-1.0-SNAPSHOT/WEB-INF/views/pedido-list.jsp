<%-- 
    Document   : pedido-list
    Created on : 12 mar 2025, 17:21:19
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
            <h2>Pedidos</h2>
            <div class="Num">
                 <a>Elementos encontrados: </a><a>${pedidoCollection.size()}</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Cliente</th>
                        <th>Código Postal</th>
                        <th>Calle</th>
                        <th>Ciudad</th>
                        <th>Provincia</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="pedido" items="${pedidoCollection}">
                        <tr>
                            <td><div class="codigo"><a href="">${pedido.codigo}</a></div></td>
                            <td>${pedido.codigoCliente}</td>
                            <td>${pedido.cp}</td>
                            <td>${pedido.calle}</td>
                            <td>${pedido.ciudad}</td>
                            <td>${pedido.provincia}</td>
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