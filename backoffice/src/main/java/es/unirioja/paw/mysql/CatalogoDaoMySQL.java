/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.pagination.PageNumberPagination;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.dao.CatalogoDAO;
/**
 *
 * @author Jarein
 */
public class CatalogoDaoMySQL implements CatalogoDAO{

    public List<Articulo> findAll() throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<Articulo> articulos = new ArrayList<Articulo>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM articulo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                articulos.add(new Articulo(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion(e);
        } finally {
            try {
                ConnectionManagerDS.returnConnection(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return articulos;
    }

    public Articulo findOneByCodigo(String codigo) throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        Articulo articulo = null;
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM articulo WHERE codigo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                articulo= new Articulo(codigo,rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion(e);
        } finally {
            try {
                ConnectionManagerDS.returnConnection(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return articulo;
    }
    public List<Articulo> findByPage(int pageNumber, int pageSize) throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<Articulo> articulos = new ArrayList<Articulo>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM articulo LIMIT ?,?";
            int desde=(pageNumber-1)*pageSize;
            ps = con.prepareStatement(sql);
             ps.setInt(1, desde);
             ps.setInt(2, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                articulos.add(new Articulo(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion(e);
        } finally {
            try {
                ConnectionManagerDS.returnConnection(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return articulos;
    }
    public PageNumberPagination buildPageNumberPagination(int pageSize, int currentPage) throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        int i=0;
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT COUNT(*) FROM articulo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                i=rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion(e);
        } finally {
            try {
                ConnectionManagerDS.returnConnection(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return new PageNumberPagination(i, pageSize, currentPage);
    }

    
}
