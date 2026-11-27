package com.manoamano.mano_a_mano.repository;

import com.manoamano.mano_a_mano.model.EstadoPago;
import com.manoamano.mano_a_mano.model.TransaccionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransaccionPagoRepository extends JpaRepository<TransaccionPago, Long> {
    Optional<TransaccionPago> findByServicioId(Long servicioId);
    List<TransaccionPago> findByClienteId(Long clienteId);
    List<TransaccionPago> findByTecnicoId(Long tecnicoId);
    List<TransaccionPago> findByEstado(EstadoPago estado);
}
