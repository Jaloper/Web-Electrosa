<%-- 
    Document   : __body_top
    Created on : 5 mar 2025, 18:12:58
    Author     : Jarein
--%>

<%@page import="es.unirioja.paw.model.Usuario"%>
<div class="topbar">
    <span class="topbar_userinfo">${usuario.getUsername()}</span>
    <span class="topbar_sep">/</span> 
    <a href="<%= request.getContextPath() %>/auth/logout" class="topbar_link">
        <i class="lni lni-power-button"></i> Cerrar sesión
    </a>
</div>

<header class="cd-main-header header">
    <a class="cd-logo" href="#0">
        <h1 class="store-branding-logo">                   
            <img src="${pageContext.request.contextPath}/assets/images/logo-electrosa-retro.png" />        
            <span>Backoffice</span>
        </h1>
    </a>

    <ul class="cd-header-buttons">
        <li><a class="cd-search-trigger" href="#cd-search">Search<span></span></a></li>
        <li><a class="cd-nav-trigger" href="#cd-primary-nav">Menu<span></span></a></li>
    </ul> <!-- cd-header-buttons -->
</header>
