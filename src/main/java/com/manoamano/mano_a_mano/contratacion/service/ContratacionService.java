package com.manoamano.mano_a_mano.contratacion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manoamano.mano_a_mano.contratacion.dto.CotizacionForm;
import com.manoamano.mano_a_mano.contratacion.dto.ServicioForm;
import com.manoamano.mano_a_mano.contratacion.model.Cotizacion;
import com.manoamano.mano_a_mano.contratacion.model.EstadoServicio;
import com.manoamano.mano_a_mano.contratacion.model.ServicioSolicitado;
import com.manoamano.mano_a_mano.contratacion.repository.CotizacionRepository;
import com.manoamano.mano_a_mano.contratacion.repository.ServicioSolicitadoRepository;

@Service
public class ContratacionService {

    private final ServicioSolicitadoRepository servicioRepository;
    private final CotizacionRepository cotizacionRepository;

    public ContratacionService(ServicioSolicitadoRepository servicioRepository,
                                CotizacionRepository cotizacionRepository) {
        this.servicioRepository = servicioRepository;
        this.cotizacionRepository = cotizacionRepository;
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

    public Cotizacion buscarCotizacion(Long id) {
        return cotizacionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cotización no encontrada: " + id));
    }

    public List<Cotizacion> listarCotizaciones(Long servicioId) {
        return cotizacionRepository.findByServicioId(servicioId);
    }

    @Transactional
    public Cotizacion pedirCotizacion(Long servicioId) {
        ServicioSolicitado servicio = buscarPorId(servicioId);
        if (servicio.getEstado() != EstadoServicio.SOLICITADO
                && servicio.getEstado() != EstadoServicio.COTIZADO) {
            throw new IllegalStateException("Este servicio ya no admite nuevas cotizaciones");
        }
        return cotizacionRepository.save(new Cotizacion(servicio));
    }

    @Transactional
    public Long responderCotizacion(Long cotizacionId, CotizacionForm form) {
        Cotizacion cotizacion = buscarCotizacion(cotizacionId);
        cotizacion.responder(form.getManoDeObra(), form.getMateriales());

        ServicioSolicitado servicio = cotizacion.getServicio();
        if (servicio.getEstado() == EstadoServicio.SOLICITADO) {
            servicio.cambiarEstado(EstadoServicio.COTIZADO);
        }
        return servicio.getId();
    }

    @Transactional
    public Long aceptarCotizacion(Long cotizacionId) {
        Cotizacion elegida = buscarCotizacion(cotizacionId);
        ServicioSolicitado servicio = elegida.getServicio();

        elegida.aceptar();
        servicio.cambiarEstado(EstadoServicio.CONTRATADO);

        for (Cotizacion otra : cotizacionRepository.findByServicioId(servicio.getId())) {
            if (!otra.getId().equals(elegida.getId())) {
                otra.rechazar();
            }
        }
        return servicio.getId();
    }

    @Transactional
    public Long rechazarCotizacion(Long cotizacionId) {
        Cotizacion cotizacion = buscarCotizacion(cotizacionId);
        cotizacion.rechazar();
        return cotizacion.getServicio().getId();
    }
}