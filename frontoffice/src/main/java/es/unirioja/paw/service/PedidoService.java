package es.unirioja.paw.service;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Servicios relacionados con pedidos
 */
@Service
public interface PedidoService {

    /**
     * @param codigoCliente Codigo del cliente
     * @return Coleccion de los pedidos del cliente (compras realizadas)
     */
    public List<PedidoEntity> findByCliente(String codigoCliente);

    /**
     * Busqueda de un pedido por su codigo
     *
     * @param codigoPedido
     * @return Pedido
     */
    public PedidoEntity findOne(String codigoPedido);

    /**
     *
     * @param codigoCliente Codigo del cliente
     * @return Cesta de la compra (coleccion de articulos en la cesta)
     */
    public CestaCompraEntity findCestaCliente(String codigoCliente);

    public void deleteCestaCliente(String codigoCliente);

     /**
     * @param codigoCliente Codigo del cliente
     * @return Coleccion de los pedidos anulados del cliente (compras rechazadas)
     */
    public List<PedidoanuladoEntity> findAnulados(String codigoCliente);
    /**
     * Busqueda de un pedido anulado por su codigo
     *
     * @param codigoPedido
     * @return Pedido
     */
    public PedidoanuladoEntity findById(String codigoPedido);

    public void anularPedido(String id);
}
