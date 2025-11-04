package es.unirioja.paw.repository;

import es.unirioja.paw.exception.PedidoNotFoundException;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PedidoAnuladoRepository extends JpaRepository<PedidoanuladoEntity, String> {

    public List<PedidoanuladoEntity> findByCodigoCliente(String s);
}
