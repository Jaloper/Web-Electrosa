/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Cliente;
import java.util.List;

/**
 *
 * @author Jarein
 */
public interface ClienteDAO {
    public List<Cliente> findAll() throws ExcepcionDeAplicacion;
    public Cliente findOneByCodigo(String clientId) throws ExcepcionDeAplicacion;
}
