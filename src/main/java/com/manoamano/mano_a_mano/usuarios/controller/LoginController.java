package com.manoamano.mano_a_mano.usuarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/cliente/inicio")
    public String inicioCliente() {
        return "cliente-inicio";
    }

    @GetMapping("/tecnico/inicio")
    public String inicioTecnico() {
        return "tecnico-inicio";
    }

    @GetMapping("/admin/inicio")
    public String inicioAdmin() {
        return "admin-inicio";
    }
}