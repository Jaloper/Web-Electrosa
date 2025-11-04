<%-- 
    Document   : articulo
    Created on : 8 mar 2025, 11:39:09
    Author     : Jarein
--%>

<%@page import="es.unirioja.paw.mysql.CatalogoDaoMySQL"%>
<%@page import="es.unirioja.paw.dao.CatalogoDAO"%>
<%@page import="java.util.List"%>
<%@page import="es.unirioja.paw.model.Articulo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%
            String codigo = request.getParameter("artId");
            CatalogoDAO dao = new CatalogoDaoMySQL();
            Articulo a = dao.findOneByCodigo(codigo);
        %>
    <%@ include file='/partials/__head.jsp'%>
    <link rel="stylesheet" href="assets/css/practica3.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <nav class="navegacion">
                <a href="welcome.html">Inicio</a> /
                <a href="catalogo.jsp">Catálogo</a> /
                <span><%= a.getCodigo()%></span>
            </nav>

            <h2>Ficha del producto</h2>


            <a href="catalogo.jsp" class="volver">Volver al catálogo</a>

            <div class="contenedor">
                <div class="imagen">
                    <img src="<%= "assets/images/store/" + a.getFoto()%>" alt="<%= a.getDescripcion()%>">
                </div>
                <div class="info">
                    <p class="fabricante"><%= a.getFabricante()%></p>
                    <h1 class="nombre"><%= a.getNombre()%></h1>
                    <p class="pvp"><strong><%= a.getPvp() + " €"%></strong></p>
                    <p class="codigo">Código: <span><%= a.getCodigo()%></span></p>
                    <p class="descripcion"><%= a.getDescripcion()%>
                    </p>
                    <a href="<%="article?artId="+a.getCodigo()%>" class="btn-editar">EDITAR PRODUCTO</a>
                </div>
            </div>

        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
