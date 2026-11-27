package com.manoamano.mano_a_mano.repository;

import com.manoamano.mano_a_mano.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    Optional<Calificacion> findByServicioId(Long servicioId);
    List<Calificacion> findByTecnicoId(Long tecnicoId);
}