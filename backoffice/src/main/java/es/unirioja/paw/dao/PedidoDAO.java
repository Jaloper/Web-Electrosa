/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
import java.util.List;

/**
 *
 * @author Jarein
 */
public interface PedidoDAO {
    public List<Pedido> findAll() throws ExcepcionDeAplicacion;
}
