package com.manoamano.mano_a_mano.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensaje_chat")
public class MensajeChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    @Column(name = "remitente_id", nullable = false)
    private Long remitenteId;

    @Column(name = "contenido", nullable = false, length = 1000)
    private String contenido;

    @Column(name = "moderado", nullable = false)
    private boolean moderado;

    @Column(name = "motivo_moderacion", length = 255)
    private String motivoModeracion;

    @Column(name = "fecha_envio", nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public Long getRemitenteId() { return remitenteId; }
    public void setRemitenteId(Long remitenteId) { this.remitenteId = remitenteId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public boolean isModerado() { return moderado; }
    public void setModerado(boolean moderado) { this.moderado = moderado; }

    public String getMotivoModeracion() { return motivoModeracion; }
    public void setMotivoModeracion(String motivoModeracion) { this.motivoModeracion = motivoModeracion; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
}
