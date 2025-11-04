<%-- 
    Document   : catalogo
    Created on : 8 mar 2025, 11:22:25
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
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="assets/css/practica3.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
           <h2>Catálogo de productos</h2>
            <%
                CatalogoDAO dao = new CatalogoDaoMySQL();
                List<Articulo> articulos = dao.findAll();
            %>
            <div class="Num">
                <a>Numero de elementos: </a><a><%=articulos.size()%></a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Imagen</th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Tipo</th>
                        <th>Fabricante</th>
                        <th>PVP</th>
                    </tr>
                </thead>

                <tbody>
                    <% for (Articulo a : articulos) {%>
                    <tr>
                        <td><a href="articulo.jsp?artId=<%= a.getCodigo()%>"><img src="<%= "assets/images/store/" + a.getFoto()%>" alt="<%= a.getDescripcion()%>" width="70" height="70"></a></td>
                        <td>
                            <div class="codigo">
                                <a href="articulo.jsp?artId=<%= a.getCodigo()%>"><%= a.getCodigo()%></a>
                            </div>
                        </td>
                        <td>
                            <a href="articulo.jsp?artId=<%= a.getCodigo()%>"><%= a.getNombre()%></a></td>
                        <td><%= a.getTipo()%></td>
                        <td><%= a.getFabricante()%></td>
                        <td><%= a.getPvp()%> €</td>
                        <% }%> </tr>
                </tbody>
            </table>
        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
