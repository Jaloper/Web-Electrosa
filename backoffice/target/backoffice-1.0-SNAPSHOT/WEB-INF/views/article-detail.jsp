<%-- 
    Document   : article-detail
    Created on : 8 mar 2025, 19:39:55
    Author     : Jarein
--%>

<%@page import="es.unirioja.paw.dao.ArticuloDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
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
            <nav class="navegacion">
                <a href="welcome.html">Inicio</a> /
                <a href="${volverUrl}">Catálogo</a> /
                <span>${articulo.codigo}</span>
            </nav>

            <h2>Ficha del producto</h2>
            <a href="${volverUrl}" class="volver">Volver al catálogo</a>
            <c:if test="${not empty errores}">
                <div class="errores">
                    <ul>
                        <c:forEach var="error" items="${errores}">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.mensajeExito}">
                <div class="mensaje-exito">
                    <p>${sessionScope.mensajeExito}</p>
                </div>
                <c:remove var="mensajeExito" />
            </c:if>

            <form action="article" method="post">
                <input type="hidden" name="artId" value="${articulo.codigo}">
                <input type="hidden" name="foto" value="${articulo.foto}">

                <div class="contenedor">
                    <div class="imagen">
                          <a href="articleImage?artId=${articulo.codigo}" class="cambiar-foto">📄 Cambiar foto</a>
                        <img src="<%= request.getContextPath()%>/assets/images/store/${articulo.foto}" alt="${articulo.descripcion}">
                    </div>
                    <div class="info">
                        <h3>Información básica</h3>
                        <div>
                            <label for="nombre" class="label-form">Nombre</label>
                            <input type="text" id="nombre" name="nombre" value="${articulo.nombre}">
                        </div>
                        <div>
                            <label for="descripcion" class="label-form">Descripción</label>
                            <textarea id="descripcion" name="descripcion" rows="4" cols="50">${articulo.descripcion}</textarea>
                        </div>
                        <div>
                            <label for="tipo" class="label-form">Tipo</label>
                            <select id="tipo" name="tipo">
                                <option value="${articulo.tipo}" selected>${articulo.tipo}</option>
                                <c:forEach var="tipo" items="${tipos}">
                                    <option value="${tipo.nombre}">${tipo.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label for="fabricante" class="label-form">Fabricante</label>
                            <select id="fabricante" name="fabricante">
                                <option value="${articulo.fabricante}" selected>${articulo.fabricante}</option>
                                <c:forEach var="fabricante" items="${fabricantes}">
                                    <option value="${fabricante.nombre}">${fabricante.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label for="precio" class="label-form">Precio (€)</label>
                            <input type="number" id="precio" name="precio" value="${articulo.pvp}" step="0.01" min="0">
                        </div>
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
