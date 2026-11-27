package com.manoamano.mano_a_mano.usuarios;

import com.manoamano.mano_a_mano.usuarios.model.CategoriaServicio;
import com.manoamano.mano_a_mano.usuarios.model.Rol;
import com.manoamano.mano_a_mano.usuarios.repository.CategoriaServicioRepository;
import com.manoamano.mano_a_mano.usuarios.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository roles;
    private final CategoriaServicioRepository categorias;

    public DataInitializer(RolRepository roles, CategoriaServicioRepository categorias) {
        this.roles = roles;
        this.categorias = categorias;
    }

    @Override
    public void run(String... args) {
        for (String nombre : new String[]{"CLIENTE", "TECNICO", "ADMINISTRADOR"}) {
            if (roles.findByNombre(nombre).isEmpty()) {
                roles.save(new Rol(null, nombre));
            }
        }

        for (String nombre : new String[]{"Electricidad", "Plomeria", "Cerrajeria", "Pintura", "Carpinteria"}) {
            if (!categorias.existsByNombre(nombre)) {
                CategoriaServicio c = new CategoriaServicio();
                c.setNombre(nombre);
                categorias.save(c);
            }
        }
    }
}