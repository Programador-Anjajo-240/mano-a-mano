package com.manoamano.mano_a_mano.contratacion.model;

import java.time.LocalDateTime;

import com.manoamano.mano_a_mano.usuarios.model.Usuario;

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
@Table(name = "servicio_solicitado")
public class ServicioSolicitado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoServicio estado = EstadoServicio.SOLICITADO;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    protected ServicioSolicitado() {
    }

    public ServicioSolicitado(String descripcion, String ubicacion) {
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public ServicioSolicitado(String descripcion, String ubicacion, Usuario cliente) {
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.cliente = cliente;
    }

    public void cambiarEstado(EstadoServicio nuevoEstado) {
        if (!estado.puedePasarA(nuevoEstado)) {
            throw new IllegalStateException(
                "No se puede pasar de " + estado + " a " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }

    public void asignarCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public EstadoServicio getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Usuario getCliente() {
        return cliente;
    }
}