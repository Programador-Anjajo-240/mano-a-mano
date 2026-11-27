package com.manoamano.mano_a_mano.repository;

import com.manoamano.mano_a_mano.model.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {
    List<TicketSoporte> findByUsuarioId(Long usuarioId);
    List<TicketSoporte> findByEstado(String estado);
}
