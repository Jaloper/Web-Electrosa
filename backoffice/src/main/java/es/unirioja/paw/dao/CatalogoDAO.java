package es.unirioja.paw.dao;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.pagination.PageNumberPagination;
import java.util.List;

public interface CatalogoDAO {

    /**
     * @return Todos los articulos
     * @throws ExcepcionDeAplicacion
     */
    public List<Articulo> findAll() throws ExcepcionDeAplicacion;

    /**
     * @param codigo Código de artículo
     * @return Articulo
     * @throws ExcepcionDeAplicacion
     */
    public Articulo findOneByCodigo(String codigo) throws ExcepcionDeAplicacion;
    
    public PageNumberPagination buildPageNumberPagination(int pageSize, int currentPage) throws ExcepcionDeAplicacion;
    
    public List<Articulo> findByPage(int pageNumber, int pageSize) throws ExcepcionDeAplicacion;

}
