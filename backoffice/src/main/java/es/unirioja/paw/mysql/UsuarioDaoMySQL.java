/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.UsuarioDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jarein
 */
public class UsuarioDaoMySQL implements UsuarioDAO{

    @Override
    public Usuario findOneByCodigo(String username) throws ExcepcionDeAplicacion{
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        Usuario usuario = null;
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT * FROM usuario WHERE username=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario= new Usuario(username); 
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
        return usuario;
    }

    @Override
    public boolean esAdmin(Usuario u) throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        Boolean esAdmin=null;
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT rol FROM rolesusuario WHERE username=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            rs = ps.executeQuery();
            if (rs.next()) {
                esAdmin= (rs.getString(1).trim().equals("administrador"));
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
        return esAdmin;
    }

    @Override
    public String getHashedPassword(Usuario u) throws ExcepcionDeAplicacion {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String password=null;
        try {
            con = ConnectionManagerDS.getConnection();
            String sql = "SELECT password FROM usuario WHERE username=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            rs = ps.executeQuery();
            if (rs.next()) {
                password= (rs.getString(1));
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
        return password;
    }
    
    
}
