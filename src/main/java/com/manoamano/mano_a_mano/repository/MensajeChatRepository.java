package com.manoamano.mano_a_mano.repository;

import com.manoamano.mano_a_mano.model.MensajeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {
    List<MensajeChat> findByServicioIdOrderByFechaEnvioAsc(Long servicioId);
}