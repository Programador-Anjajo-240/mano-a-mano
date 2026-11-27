package com.manoamano.mano_a_mano.contratacion.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioSolicitado servicio;

    @Column(precision = 12, scale = 2)
    private BigDecimal manoDeObra;

    @Column(precision = 12, scale = 2)
    private BigDecimal materiales;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCotizacion estado = EstadoCotizacion.PENDIENTE;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    protected Cotizacion() {
    }

    public Cotizacion(ServicioSolicitado servicio) {
        this.servicio = servicio;
    }

    public void responder(BigDecimal manoDeObra, BigDecimal materiales) {
        if (estado != EstadoCotizacion.PENDIENTE) {
            throw new IllegalStateException("Solo se puede responder una cotización pendiente");
        }
        this.manoDeObra = manoDeObra;
        this.materiales = materiales;
        this.estado = EstadoCotizacion.RESPONDIDA;
    }

    public BigDecimal getTotal() {
        return manoDeObra.add(materiales);
    }

    public Long getId() {
        return id;
    }

    public ServicioSolicitado getServicio() {
        return servicio;
    }

    public BigDecimal getManoDeObra() {
        return manoDeObra;
    }

    public BigDecimal getMateriales() {
        return materiales;
    }

    public EstadoCotizacion getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}