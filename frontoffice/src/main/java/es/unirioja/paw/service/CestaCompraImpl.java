/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

import es.unirioja.paw.exception.ArticuloNotFoundException;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.DireccionEntity;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;
import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import es.unirioja.paw.repository.CestaCompraRepository;
import es.unirioja.paw.repository.PedidoRepository;
import es.unirioja.paw.service.data.AddToCartRequest;
import es.unirioja.paw.service.data.AddToCartResponse;
import es.unirioja.paw.service.data.FinalizarCompraRequest;
import es.unirioja.paw.service.data.FinalizarCompraResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jarein
 */
@Service
public class CestaCompraImpl implements CestaCompraUseCase {

    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private CestaCompraRepository cestaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Optional<AddToCartResponse> add(AddToCartRequest r) {
        // 1. Buscar el artículo en la cesta
        Optional<LineaCestaCompraEntity> lineaArticulo = r.cesta.findLineaArticulo(r.codigoArticulo);

        LineaCestaCompraEntity linea;

        if (lineaArticulo.isPresent()) {
            // 2a. Si el artículo ya estaba en la cesta, se incrementa
            linea = lineaArticulo.get();
            linea.setCantidad(linea.getCantidad() + 1);
        } else {
            // 2b. Si el artículo no estaba todavía, se crea la línea
            ArticuloEntity articulo = articuloRepository.findById(r.codigoArticulo)
                    .orElseThrow(() -> new ArticuloNotFoundException(r.codigoArticulo));

            linea = new LineaCestaCompraEntity();
            linea.setArticulo(articulo);
            linea.setCantidad(1);
            linea.setPrecio(articulo.getPvp());
        }

        // 3. Se añade a la entidad Cesta
        linea.setCesta(r.cesta);
        if (!lineaArticulo.isPresent()) {
            r.cesta.getLineas().add(linea);
        }

        // 4. Se guarda en BD
        r.cesta = cestaRepository.save(r.cesta);
        return Optional.of(new AddToCartResponse(r.cesta, linea));
    }

    @Override
    @Transactional
    public FinalizarCompraResponse finalizarCompra(FinalizarCompraRequest request) {
            CestaCompraEntity cesta = cestaRepository.findById(request.cesta.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

            if (cesta.getLineas() == null || cesta.getLineas().isEmpty()) {
                return null;
            }

            PedidoEntity pedido = new PedidoEntity();
            pedido.setCodigo(generarCodigoPedido());
            pedido.setCodigoCliente(request.cesta.getCodigoCliente());
            pedido.setFechacierre(new Date(System.currentTimeMillis()));
            pedido.setCursado(1);
            
            DireccionEntity dir = new DireccionEntity();
            dir.setCalle(request.calle);
            dir.setCiudad(request.ciudad);
            dir.setCp(request.codigoPostal);
            dir.setProvincia(request.provincia);
            pedido.setDireccion(dir);
            
            List<LineaPedidoEntity> lineas=new ArrayList<>();
            int numLinea = 1;
            for (LineaCestaCompraEntity lineaCesta : request.cesta.getLineas()) {
                LineaPedidoEntity lineaPedido = new LineaPedidoEntity();
                lineaPedido.setCodigo(generarCodigoLinea(pedido, numLinea));
                lineaPedido.setPedido(pedido);
                lineaPedido.setArticulo(lineaCesta.getArticulo());
                lineaPedido.setCantidad(lineaCesta.getCantidad());
                lineaPedido.setPrecioBase(lineaCesta.getPrecio());
                lineaPedido.setPrecioReal(lineaCesta.getPrecio());
                
                LocalDate fechaLocal = request.fechaEntregaDeseada;
                java.sql.Date sqlDate = java.sql.Date.valueOf(fechaLocal);
                lineaCesta.setFechaEntregaDeseada(sqlDate);
                pedido.buildImporte();
                lineas.add(lineaPedido);
                numLinea++;
            }
            pedido.setLineas(lineas);
            PedidoEntity pedidoGuardado = pedidoRepository.save(pedido);

            return new FinalizarCompraResponse(pedidoGuardado);

    }

    public String generarCodigoPedido() {
    String yearSuffix = String.valueOf(LocalDate.now().getYear()).substring(2);
    
    long nextNumber = getPedId(); 
    
    String numberPart = String.format("P%06d", nextNumber);
    
    return numberPart + "-" + yearSuffix;
}
    private long getPedId() {
     List<PedidoEntity> pedidos = pedidoRepository.findAll(Sort.by(Sort.Direction.DESC, "codigo"));
    
    if (!pedidos.isEmpty()) {
        String codigo = pedidos.get(0).getCodigo();
        String numeroStr = codigo.substring(1, 7);
        return Long.parseLong(numeroStr) + 1;
    }
    return 1;
}
    public String generarCodigoLinea(PedidoEntity pedido, int numeroLinea) {
    String yearSuffix = String.valueOf(LocalDate.now().getYear()).substring(2);
    
    String ciudad = pedido.getDireccion().getCiudad();
    char letraCiudad = ciudad != null && !ciudad.isEmpty() 
            ? ciudad.toUpperCase().charAt(0) : 'X';
    
    String numberPart = String.format("%c%06d", letraCiudad, numeroLinea);
    return numberPart + "-" + yearSuffix;
}

@Override
public void actualizarCantidad(CestaCompraEntity cesta, String codigoArticulo, int quantity) {
    if (cesta == null || cesta.getLineas() == null) return;

    cesta.getLineas().removeIf(linea -> linea.getArticulo().getCodigo().equals(codigoArticulo) && quantity <= 0);

    // Si había línea y quantity > 0, la actualizamos
    for (LineaCestaCompraEntity linea : cesta.getLineas()) {
        if (linea.getArticulo().getCodigo().equals(codigoArticulo)) {
            linea.setCantidad(quantity);
            break;
        }
    }
    // **¡Sin llamar a cestaRepository.save!**  
}



}
