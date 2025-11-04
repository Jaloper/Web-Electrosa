/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.PedidoDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
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
public class PedidoDaoMySQL implements PedidoDAO {
    public List<Pedido> findAll() throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<Pedido> lista = new ArrayList<Pedido>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM pedido";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Pedido(rs.getString(1),rs.getString(2),rs.getString(5),rs.getString(4),rs.getString(6),rs.getString(7)));
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
