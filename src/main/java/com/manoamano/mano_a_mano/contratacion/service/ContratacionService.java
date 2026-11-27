package com.manoamano.mano_a_mano.contratacion.service;

import org.springframework.stereotype.Service;

import com.manoamano.mano_a_mano.contratacion.dto.ServicioForm;
import com.manoamano.mano_a_mano.contratacion.model.ServicioSolicitado;
import com.manoamano.mano_a_mano.contratacion.repository.ServicioSolicitadoRepository;

@Service
public class ContratacionService {

    private final ServicioSolicitadoRepository servicioRepository;

    public ContratacionService(ServicioSolicitadoRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public ServicioSolicitado crearServicio(ServicioForm form) {
        ServicioSolicitado servicio =
            new ServicioSolicitado(form.getDescripcion(), form.getUbicacion());
        return servicioRepository.save(servicio);
    }

    public ServicioSolicitado buscarPorId(Long id) {
        return servicioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + id));
    }
}