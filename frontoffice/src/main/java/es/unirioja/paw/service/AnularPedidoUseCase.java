package es.unirioja.paw.service;

import es.unirioja.paw.service.data.AnularPedidoRequest;
import es.unirioja.paw.service.data.AnularPedidoResponse;

public interface AnularPedidoUseCase {

    /**
     * Guarda un nuevo pedido anulado en base a un pedido, 
     * eliminando finalmente el pedido original 
     * @param request Informacion sobre el pedido a anular
     * @return Agregado con el pedido anulado
     */
    public AnularPedidoResponse execute(AnularPedidoRequest request);

}
