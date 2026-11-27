package com.manoamano.mano_a_mano.usuarios.repository;

import com.manoamano.mano_a_mano.usuarios.model.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    boolean existsByNombre(String nombre);   
}
