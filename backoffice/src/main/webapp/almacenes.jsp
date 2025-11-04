<%-- 
    Document   : almacenes
    Created on : 8 mar 2025, 11:31:26
    Author     : Jarein
--%>

<%@page import="es.unirioja.paw.model.Almacen"%>
<%@page import="es.unirioja.paw.mysql.AlmacenDaoMySQL"%>
<%@page import="es.unirioja.paw.dao.AlmacenDAO"%>
<%@page import="es.unirioja.paw.mysql.CatalogoDaoMySQL"%>
<%@page import="es.unirioja.paw.dao.CatalogoDAO"%>
<%@page import="java.util.List"%>
<%@page import="es.unirioja.paw.model.Articulo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
    <%@ include file='/partials/__head.jsp'%>
    <link rel="stylesheet" href="assets/css/practica3.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h2>Almacenes</h2>
            <section class="card-layout">
                <!-- Iteración sobre los almacenes -->
                <%
                    AlmacenDAO dao = new AlmacenDaoMySQL();
                    List<Almacen> almacenes = dao.findAll();

                    for (Almacen a : almacenes) {
                        %>
                <div class="card-layout__item">
                    <span class="card-layout__codigo"><%= a.getCodigo() %></span>
                    <img src="<%= "assets/images/warehouse/" +"512px-"+a.getCodigo()+".jpg"%>" alt="<%= a.getCalle()%>" width="70" height="70">
                    <h3><%=a.getCiudad()%></h3>
                    <p><%="("+a.getProvincia()+")"%></p>
                    <div>
                <input type="submit" value="Cambiar" />
                 </div>
                </div>
                <% }%>
            </section>
        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
