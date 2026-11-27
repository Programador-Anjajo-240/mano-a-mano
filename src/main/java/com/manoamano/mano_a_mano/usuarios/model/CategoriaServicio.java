package com.manoamano.mano_a_mano.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias_servicio")
@Getter @Setter @NoArgsConstructor
public class CategoriaServicio {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 60)
    private String nombre;
}