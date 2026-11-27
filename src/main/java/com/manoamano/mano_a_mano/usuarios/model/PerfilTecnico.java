package com.manoamano.mano_a_mano.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfiles_tecnico")
@Getter @Setter @NoArgsConstructor
public class PerfilTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoTecnico estado = EstadoTecnico.PENDIENTE;

    private Integer aniosExperiencia;

    @Column(length = 1000)
    private String descripcion;

    private Double latitud;
    private Double longitud;
    private Double promedioCalificacion;
    private String documentosUrl;
    private String motivoRechazo;
}