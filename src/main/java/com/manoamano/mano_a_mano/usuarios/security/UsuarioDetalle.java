package com.manoamano.mano_a_mano.usuarios.security;

import com.manoamano.mano_a_mano.usuarios.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class UsuarioDetalle implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetalle(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() { return usuario; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));
    }

    @Override public String getPassword() { return usuario.getContrasena(); }
    @Override public String getUsername() { return usuario.getCorreo(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.getBloqueadoHasta() == null
                || usuario.getBloqueadoHasta().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() { return usuario.isActivo(); }
}