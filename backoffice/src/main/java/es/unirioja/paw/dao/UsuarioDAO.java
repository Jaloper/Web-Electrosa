/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Usuario;

/**
 *
 * @author Jarein
 */
public interface UsuarioDAO {

    public Usuario findOneByCodigo(String username) throws ExcepcionDeAplicacion;
    public boolean esAdmin(Usuario u) throws ExcepcionDeAplicacion;
    public String getHashedPassword(Usuario u) throws ExcepcionDeAplicacion;
}
