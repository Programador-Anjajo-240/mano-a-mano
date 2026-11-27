package com.manoamano.mano_a_mano.usuarios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    private String telefono;
    private String fotoUrl;

    private boolean activo = false;
    private int intentosFallidos = 0;
    private LocalDateTime bloqueadoHasta;
    private LocalDateTime creadoEn = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id")
    private Rol rol;
}