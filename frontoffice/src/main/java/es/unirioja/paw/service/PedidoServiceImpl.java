package es.unirioja.paw.service;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.LineaanuladaEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.repository.CestaCompraRepository;
import es.unirioja.paw.repository.PedidoAnuladoRepository;
import es.unirioja.paw.repository.PedidoRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoAnuladoRepository pedidoAnuladoRepository;
    private CestaCompraRepository cestaRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, CestaCompraRepository cestaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.cestaRepository = cestaRepository;
    }

    @Override
    public List<PedidoEntity> findByCliente(String codigoCliente) {
        PedidoEntity example = new PedidoEntity();
        example.setCodigoCliente(codigoCliente);
        List<PedidoEntity> itemCollection = pedidoRepository.findAll(Example.of(example));
        if (itemCollection != null) {
            for (PedidoEntity e : itemCollection) {
                e.buildImporte();
            }
        }
        logger.info("Cliente {}: {} pedidos", codigoCliente, itemCollection.size());
        return itemCollection;
    }

    @Override
    public PedidoEntity findOne(String codigoPedido) {
        PedidoEntity pedido = null;
        Optional<PedidoEntity> entity = pedidoRepository.findById(codigoPedido);
        if (entity.isPresent()) {
            pedido = entity.get();
            pedido.buildImporte();
        }
        return pedido;
    }

    @Override
    public CestaCompraEntity findCestaCliente(String codigoCliente) {
        CestaCompraEntity example = new CestaCompraEntity();
        example.setCodigoCliente(codigoCliente);
        Optional<CestaCompraEntity> cesta = cestaRepository.findOne(Example.of(example));
        if (cesta.isPresent()) {
            logger.info("Cliente {}: cesta {}", codigoCliente, cesta.get().getCodigo());
            return cesta.get();
        }
        logger.info("Cliente {}: todavia no tiene cesta", codigoCliente);
        return null;
    }

    @Override
    public void deleteCestaCliente(String codigoCliente) {
        CestaCompraEntity cesta = cestaRepository.findOneByCodigoCliente(codigoCliente);
        if (cesta == null) {
            logger.info("Cliente {}: no hay cesta", codigoCliente);
            return;
        }
        logger.info("Cliente {}: cesta {}", codigoCliente, cesta.getCodigo());
        cestaRepository.deleteById(cesta.getCodigo());
    }

    @Override
    public List<PedidoanuladoEntity> findAnulados(String codigoCliente) {
        PedidoanuladoEntity example = new PedidoanuladoEntity();
        example.setCodigoCliente(codigoCliente);
        List<PedidoanuladoEntity> itemCollection = pedidoAnuladoRepository.findByCodigoCliente(codigoCliente);
        logger.info("Cliente {}: {} pedidosAnulados", codigoCliente, itemCollection.size());
        return itemCollection;
    }

    @Override
    public PedidoanuladoEntity findById(String codigoPedido) {
        PedidoanuladoEntity pedidoanulado = null;
        Optional<PedidoanuladoEntity> entity = pedidoAnuladoRepository.findById(codigoPedido);
        if (entity.isPresent()) {
            pedidoanulado = entity.get();
        }
        return pedidoanulado;
    }

    @Override
    public void anularPedido(String id) {
       Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(id);

    if (!pedidoOptional.isPresent()) {
        logger.warn("Intento de anulación fallido: pedido con id {} no encontrado", id);
        return;
    }
    PedidoEntity pedido = pedidoOptional.get();
    
    PedidoanuladoEntity pedidoAnulado = new PedidoanuladoEntity();
    pedidoAnulado.setCodigo(pedido.getCodigo());
    pedidoAnulado.setFechacierre(pedido.getFechacierre());
    pedidoAnulado.setFechaanulacion(new Date(System.currentTimeMillis()));
    pedidoAnulado.setCodigoCliente(pedido.getCodigoCliente());
    
    List<LineaanuladaEntity> nuevasLineas=new ArrayList<>();
    for(LineaPedidoEntity l:pedido.getLineas()){
        LineaanuladaEntity nuevaLinea=new LineaanuladaEntity();
        nuevaLinea.setCodigo(l.getCodigo());
        nuevaLinea.setCantidad(l.getCantidad());
        nuevaLinea.setArticulo(l.getArticulo());
        nuevaLinea.setPedido(pedidoAnulado);
        nuevasLineas.add(nuevaLinea);
    }
    pedidoAnulado.setLineas(nuevasLineas);

    pedidoAnuladoRepository.save(pedidoAnulado);
    pedidoRepository.deleteById(id);

    logger.info("Pedido con id {} anulado correctamente", id);
    }

}
