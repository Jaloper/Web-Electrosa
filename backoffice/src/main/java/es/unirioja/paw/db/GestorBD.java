/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorBD {
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/electrosa";
    private static final String USR = "root";
    private static final String PWD = "root";

    public List<String> getArticulosLike(String nombre) throws
            SQLException {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        List<String> lista = new ArrayList();
        try {
            con = DriverManager.getConnection(URL, USR, PWD);
            String sql = "SELECT nombre FROM articulo WHERE nombre LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString(1));
            }
            rs.close();
            ps.close();
        }finally {
                if (con != null) {
                    con.close();
                }
        }
        return lista;
    }
    }
