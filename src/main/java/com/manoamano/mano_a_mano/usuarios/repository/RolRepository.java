package com.manoamano.mano_a_mano.usuarios.repository;

import com.manoamano.mano_a_mano.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}