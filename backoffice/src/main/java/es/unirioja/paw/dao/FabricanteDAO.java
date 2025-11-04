/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Fabricante;
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
public interface FabricanteDAO {
    public static List<Fabricante> findAll() throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<Fabricante> fabricantes = new ArrayList<Fabricante>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT DISTINCT fabricante FROM articulo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                fabricantes.add(new Fabricante(rs.getString(1)));
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
        return fabricantes;

    }
}
