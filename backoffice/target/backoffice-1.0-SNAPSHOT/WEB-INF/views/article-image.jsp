<%-- 
    Document   : article-image
    Created on : 18 mar 2025, 8:42:48
    Author     : Jarein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="assets/css/practica4.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <nav class="navegacion">
                <a href="<%= request.getContextPath()%>/article?artId=${codigo}">Volver al artículo ${codigo}</a>
            </nav>

            <h2>Foto del producto</h2>

            <form action="articleImage" method="post"  enctype="multipart/form-data">>
                <input type="hidden" name="codigo" value="${codigo}">
                <input type="hidden" name="rutaImagen" value="${rutaImagen}">

                <div class="contenedor">
                    <div class="imagen">
                        <img src="<%= request.getContextPath()%>/assets/images/store/${rutaImagen}" alt="${descripcion}">
                    </div>
                    <div class="info">
                        <h3>Adjuntar foto</h3>
                        <input type="file" name="imagen" accept="image/*" >
                        <input class="btn-editar" type="submit" value="GUARDAR CAMBIOS">
                    </div>
                </div>
            </form>
        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>

