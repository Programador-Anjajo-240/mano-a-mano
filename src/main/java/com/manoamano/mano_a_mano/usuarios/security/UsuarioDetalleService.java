package com.manoamano.mano_a_mano.usuarios.security;

import com.manoamano.mano_a_mano.usuarios.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalleService implements UserDetailsService {

    private final UsuarioRepository usuarios;

    public UsuarioDetalleService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return usuarios.findByCorreo(correo.trim().toLowerCase())
                .map(UsuarioDetalle::new)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));
    }
}