<%-- 
    Document   : article-list
    Created on : 8 mar 2025, 13:38:49
    Author     : Jarein
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@ include file='/partials/__head.jsp'%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/practica4.css">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h2>Catálogo de productos</h2>
            <div class="paginator-wrapper">
                <c:if test="${pagination.first() != pagination.currentPage}">
                    <a href="?p=${pagination.first()}"><i class="lni lni-shift-left"></i> Primero</a>
                </c:if>
                <c:if test="${pagination.previous() != null}">
                    <a href="?p=${pagination.previous()}">Anterior</a>
                </c:if>
                <span class="current">
                    Mostrando página ${pagination.currentPage} de ${pagination.totalPages}
                </span>
                <c:if test="${pagination.next() != null}">
                    <a href="?p=${pagination.next()}">Siguiente</a>
                </c:if>
                <c:if test="${pagination.last() != pagination.currentPage}">
                    <a href="?p=${pagination.last()}">Último <i class="lni lni-shift-right"></i></a>
                </c:if>
            </div>

            <div class="pagination-info">
                <label>
                    Artículos por página:
                </label>
                <span class="pagesize">${pagination.pageSize}</span>
                <span class="records-found">(${pagination.totalCount} en total)</span>
            </div>

            <!-- Contenedor único para todas las tarjetas -->
            <section class="card-layout">
                <c:forEach var="articulo" items="${articulos}" varStatus="status">
                    <div class="card-layout__item clg-item">
                        <div class="card-layout__codigo">${articulo.codigo} (${status.index + 1})</div>
                        <div class="clg-item__image">
                            <a href="article?artId=${articulo.codigo}">
                                <img src="assets/images/store/${articulo.foto}" alt="${articulo.descripcion}" />
                            </a>
                        </div>
                        <div class="clg-item__title">${articulo.nombre}</div>
                    </div> <!-- #product item-->
                </c:forEach>
            </section> <!-- Fin del contenedor único -->
        </main>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
