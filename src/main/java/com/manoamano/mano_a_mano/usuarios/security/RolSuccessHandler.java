package com.manoamano.mano_a_mano.usuarios.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RolSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication auth) throws IOException {
        String destino = "/cliente/inicio";
        for (var a : auth.getAuthorities()) {
            if (a.getAuthority().equals("ROLE_ADMINISTRADOR")) destino = "/admin/inicio";
            else if (a.getAuthority().equals("ROLE_TECNICO")) destino = "/tecnico/inicio";
        }
        response.sendRedirect(request.getContextPath() + destino);
    }
}