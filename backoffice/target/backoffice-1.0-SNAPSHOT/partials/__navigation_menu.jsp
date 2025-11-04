<%-- 
    Document   : __navigation_menu
    Created on : 5 mar 2025, 18:13:13
    Author     : Jarein
--%>
<nav class="cd-nav ">
    <ul id="cd-primary-nav" class="cd-primary-nav is-fixed">

        <li class="has-children">
            <a href="#"><i class="lni lni-rocket-5"></i> Ventas</a>

            <ul class="cd-nav-gallery is-hidden">
                <li class="go-back"><a href="#0">Menu</a></li>
                <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                <li>
                    <a class="cd-nav-item" href="<%= request.getContextPath() %>/pedidos">
                        <img src="${pageContext.request.contextPath}/assets/images/megamenu/pedido.png" alt="Product Image">
                        <h3>Pedidos</h3>
                    </a>

                </li>

                <li>
                    <a class="cd-nav-item" href="#">
                        <img src="../assets/images/megamenu/img.jpg" alt="Product Image">
                        <h3>Almacenes</h3>
                    </a>
                </li>
                <li>
                    <a class="cd-nav-item" href="#">
                        <img src="../assets/images/megamenu/img.jpg" alt="Product Image">
                        <h3>Envíos</h3>
                    </a>
                </li>
                <li>
                    <a class="cd-nav-item" href="<%= request.getContextPath() %>/clientes">
                        <img src="${pageContext.request.contextPath}/assets/images/megamenu/clientes.jpg" alt="Product Image">
                        <h3>Clientes</h3>
                    </a>
                </li>


            </ul>
        </li>

        <li class="has-children">
            <a href="#"><i class="lni lni-dashboard-square-1"></i> Catálogo</a>
            <ul class="cd-nav-icons is-hidden">
                <li class="go-back"><a href="#0">Menu</a></li>
                <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                <li>
                    <a class="cd-nav-item item-9" href="#">
                        <h3>Productos</h3>
                        <p>Todos los productos</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-10" href="#">
                        <h3>Categorías</h3>
                        <p>Categorías / Tipos de producto</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-11" href="#">
                        <h3>Fabricantes</h3>
                        <p>Fabricantes y proveedores</p>
                    </a>
                </li>


            </ul>
        </li>

        <li class="has-children">
            <a href="#"><i class="lni lni-rocket-5"></i> Analática</a>

            <ul class="cd-nav-gallery is-hidden">
                <li class="go-back"><a href="#0">Menu</a></li>
                <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                <li>
                    <a class="cd-nav-item" href="#">
                        <img src="../assets/images/megamenu/img.jpg" alt="Product Image">
                        <h3>Cuadro de mando</h3>
                    </a>
                </li>
                <li>
                    <a class="cd-nav-item" href="#">
                        <img src="../assets/images/megamenu/img.jpg" alt="Product Image">
                        <h3>Marketing</h3>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item" href="#">
                        <img src="../assets/images/megamenu/img.jpg" alt="Product Image">
                        <h3>Facturación</h3>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item" href="#">
                        <img src="../assets/images/megamenu/img.jpg" alt="Product Image">
                        <h3>Finanzas</h3>
                    </a>
                </li>
            </ul>
        </li>

        <li class="has-children">
            <a href="#"><i class="lni lni-sliders-horizontal-square-2"></i> Ajustes</a>
            <ul class="cd-nav-icons is-hidden">
                <li class="go-back"><a href="#0">Menu</a></li>
                <li>
                    <a class="cd-nav-item item-1" href="#">
                        <h3>Usuarios</h3>
                        <p>Usuarios del sistema</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-2" href="#">
                        <h3>Rendimiento</h3>
                        <p>Optimizaciones, cache y módulos</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-3" href="#">
                        <h3>Importar</h3>
                        <p>Importación de datos en el catálogo</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-4" href="#">
                        <h3>Servicios web</h3>
                        <p>API de servicios web REST</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-5" href="#">
                        <h3>Seguridad</h3>
                        <p>Parámetros de seguridad de la tienda online</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-6" href="#">
                        <h3>Base de datos</h3>
                        <p>Gestor simple SQL y copias de seguridad</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-7" href="#">
                        <h3>Información</h3>
                        <p>Información del sistema</p>
                    </a>
                </li>

                <li>
                    <a class="cd-nav-item item-8" href="#">
                        <h3>Logs</h3>
                        <p>Registros de errores y auditoría</p>
                    </a>
                </li>
            </ul>
        </li>
    </ul> <!-- primary-nav -->

</nav> <!-- cd-nav -->