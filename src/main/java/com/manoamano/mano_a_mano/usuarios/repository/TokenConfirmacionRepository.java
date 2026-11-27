package com.manoamano.mano_a_mano.usuarios.repository;

import com.manoamano.mano_a_mano.usuarios.model.TokenConfirmacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenConfirmacionRepository extends JpaRepository<TokenConfirmacion, Long> {
    Optional<TokenConfirmacion> findByToken(String token);
}