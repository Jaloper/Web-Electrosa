/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.mysql.ConnectionManagerDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jarein
 */
public interface ArticuloDAO {
    public static List<TipoArticulo> findTiposArticulos() throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<TipoArticulo> tipos = new ArrayList<TipoArticulo>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT DISTINCT tipo FROM articulo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                tipos.add(new TipoArticulo(rs.getString(1)));
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
        return tipos;
    }
    public boolean saveEntity(Articulo a) throws ExcepcionDeAplicacion ;
}
