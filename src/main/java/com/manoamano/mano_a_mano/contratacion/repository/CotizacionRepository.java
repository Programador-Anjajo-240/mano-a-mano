package com.manoamano.mano_a_mano.contratacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoamano.mano_a_mano.contratacion.model.Cotizacion;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    List<Cotizacion> findByServicioId(Long servicioId);
}