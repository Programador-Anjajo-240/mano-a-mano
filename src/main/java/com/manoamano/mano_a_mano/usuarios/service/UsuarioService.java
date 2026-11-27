package com.manoamano.mano_a_mano.usuarios.service;

import com.manoamano.mano_a_mano.usuarios.dto.RegistroClienteForm;
import com.manoamano.mano_a_mano.usuarios.exception.ReglaNegocioException;
import com.manoamano.mano_a_mano.usuarios.model.Rol;
import com.manoamano.mano_a_mano.usuarios.model.TokenConfirmacion;
import com.manoamano.mano_a_mano.usuarios.model.Usuario;
import com.manoamano.mano_a_mano.usuarios.repository.RolRepository;
import com.manoamano.mano_a_mano.usuarios.repository.TokenConfirmacionRepository;
import com.manoamano.mano_a_mano.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarios;
    private final RolRepository roles;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfirmacionRepository tokens;
    private final CorreoService correoService;

    public UsuarioService(UsuarioRepository usuarios,
                          RolRepository roles,
                          PasswordEncoder passwordEncoder, 
                          TokenConfirmacionRepository tokens, 
                          CorreoService correoService) {
        this.usuarios = usuarios;
        this.roles = roles;
        this.passwordEncoder = passwordEncoder;
        this.tokens = tokens;
        this.correoService = correoService;
    }

    @Transactional
    public Usuario registrarCliente(RegistroClienteForm form) {
        if (!form.getContrasena().equals(form.getConfirmarContrasena())) {
            throw new ReglaNegocioException("Las contraseñas no coinciden");
        }

        String correo = form.getCorreo().trim().toLowerCase();
        if (usuarios.existsByCorreo(correo)) {
            throw new ReglaNegocioException("Ese correo ya está registrado");
        }

        Rol rolCliente = roles.findByNombre("CLIENTE")
                .orElseThrow(() -> new ReglaNegocioException("No existe el rol CLIENTE"));

        Usuario u = new Usuario();
        u.setNombre(form.getNombre().trim());
        u.setCorreo(correo);
        u.setTelefono(form.getTelefono());
        u.setContrasena(passwordEncoder.encode(form.getContrasena()));
        u.setRol(rolCliente);
        u.setActivo(false);

        Usuario guardado = usuarios.save(u);

        TokenConfirmacion t = new TokenConfirmacion();
        t.setToken(java.util.UUID.randomUUID().toString());
        t.setUsuario(guardado);
        t.setExpiraEn(java.time.LocalDateTime.now().plusHours(24));
        tokens.save(t);

        correoService.enviarConfirmacion(guardado.getCorreo(), guardado.getNombre(), t.getToken());
        return guardado;  
    }
    @Transactional
    public void confirmarCuenta(String token) {
    TokenConfirmacion t = tokens.findByToken(token)
            .orElseThrow(() -> new ReglaNegocioException("Enlace inválido"));
    if (t.getExpiraEn().isBefore(java.time.LocalDateTime.now())) {
        throw new ReglaNegocioException("El enlace venció. Regístrate de nuevo");
    }
    Usuario u = t.getUsuario();
    u.setActivo(true);
    usuarios.save(u);
    tokens.delete(t);
}
}