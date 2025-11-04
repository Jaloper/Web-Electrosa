/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.ClienteDAO;
import es.unirioja.paw.dao.PedidoDAO;
import es.unirioja.paw.model.Cliente;
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
public class ClienteDaoMySQL implements ClienteDAO{
    public List<Cliente> findAll() throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<Cliente> lista = new ArrayList<Cliente>();
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM cliente"; //Podría coger solo los campos necesarios,pero como tengo que construir Clientes igualmente lo veo un poco absurdo.
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Cliente(rs.getString(1),rs.getString(10),rs.getString(3),rs.getString(7),rs.getString(5),rs.getString(6),rs.getString(8)));
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

    @Override
    public Cliente findOneByCodigo(String clientId) throws ExcepcionDeAplicacion{
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM cliente WHERE codigo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, clientId);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente= new Cliente(rs.getString(1),rs.getString(10),rs.getString(3),rs.getString(7),rs.getString(5),rs.getString(6),rs.getString(8));
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
        return cliente;
    }
}
