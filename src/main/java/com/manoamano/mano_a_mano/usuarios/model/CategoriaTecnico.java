package com.manoamano.mano_a_mano.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias_tecnico")
@Getter @Setter @NoArgsConstructor
public class CategoriaTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "perfil_id")
    private PerfilTecnico perfil;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private CategoriaServicio categoria;
}