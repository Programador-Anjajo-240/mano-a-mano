package com.manoamano.mano_a_mano.usuarios.security;

import com.manoamano.mano_a_mano.usuarios.repository.UsuarioRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class LoginAttemptListener {

    private static final int MAX_INTENTOS = 5;
    private static final int MINUTOS_BLOQUEO = 15;

    private final UsuarioRepository usuarios;

    public LoginAttemptListener(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    @EventListener
    @Transactional
    public void alFallar(AuthenticationFailureBadCredentialsEvent evento) {
        String correo = evento.getAuthentication().getName().trim().toLowerCase();
        usuarios.findByCorreo(correo).ifPresent(u -> {
            int intentos = u.getIntentosFallidos() + 1;
            u.setIntentosFallidos(intentos);
            if (intentos >= MAX_INTENTOS) {
                u.setBloqueadoHasta(LocalDateTime.now().plusMinutes(MINUTOS_BLOQUEO));
                u.setIntentosFallidos(0);
            }
            usuarios.save(u);
        });
    }

    @EventListener
    @Transactional
    public void alEntrar(AuthenticationSuccessEvent evento) {
        String correo = evento.getAuthentication().getName().trim().toLowerCase();
        usuarios.findByCorreo(correo).ifPresent(u -> {
            if (u.getIntentosFallidos() != 0 || u.getBloqueadoHasta() != null) {
                u.setIntentosFallidos(0);
                u.setBloqueadoHasta(null);
                usuarios.save(u);
            }
        });
    }
}