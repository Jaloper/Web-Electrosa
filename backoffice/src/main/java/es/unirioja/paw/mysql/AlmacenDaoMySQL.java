/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;
import es.unirioja.paw.dao.AlmacenDAO;
import es.unirioja.paw.model.Almacen;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
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
public class AlmacenDaoMySQL implements AlmacenDAO{

    public List<Almacen> findAll() throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<Almacen> lista = new ArrayList<Almacen>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM almacen";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Almacen(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
        return lista;
    }
    
}
