package es.unirioja.paw.service;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.service.data.AddToCartRequest;
import es.unirioja.paw.service.data.AddToCartResponse;
import es.unirioja.paw.service.data.FinalizarCompraRequest;
import es.unirioja.paw.service.data.FinalizarCompraResponse;
import java.util.Optional;

public interface CestaCompraUseCase {

    public Optional<AddToCartResponse> add(AddToCartRequest r);
    
    public FinalizarCompraResponse finalizarCompra(FinalizarCompraRequest r);

    public void actualizarCantidad(CestaCompraEntity cesta, String codigoArticulo, int quantity);

}
