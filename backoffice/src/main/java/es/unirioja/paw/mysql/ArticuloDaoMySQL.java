/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Jarein
 */
public class ArticuloDaoMySQL implements ArticuloDAO {

    @Override
    public boolean saveEntity(Articulo a) throws ExcepcionDeAplicacion {
        Connection con = null;
        PreparedStatement stmt1 = null;
        int i = 0;
        String sql1 = "UPDATE articulo SET nombre = ?, pvp = ?, tipo = ?, fabricante = ?, foto = ?, descripcion = ? WHERE codigo = ?;";
        try {
            con = ConnectionManagerDS.getConnection();
            stmt1 = con.prepareStatement(sql1);
            stmt1.setString(1, a.getNombre());
            stmt1.setDouble(2, a.getPvp());
            stmt1.setString(3, a.getTipo());
            stmt1.setString(4, a.getFabricante());
            stmt1.setString(5, a.getFoto());
            stmt1.setString(6, a.getDescripcion());
            stmt1.setString(7, a.getCodigo());
            i = stmt1.executeUpdate();
            stmt1.close();
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
        return i == 1;
    }

}
