package es.unirioja.paw.repository;

import es.unirioja.paw.jpa.OfertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfertaArticuloRepository extends JpaRepository<OfertaEntity, String> {
}
