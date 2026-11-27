package com.manoamano.mano_a_mano.usuarios.controller;

import com.manoamano.mano_a_mano.usuarios.dto.RegistroClienteForm;
import com.manoamano.mano_a_mano.usuarios.exception.ReglaNegocioException;
import com.manoamano.mano_a_mano.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("form", new RegistroClienteForm());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("form") RegistroClienteForm form,
                                   BindingResult errores,
                                   Model model) {
        if (errores.hasErrors()) {
            return "registro";
        }
        try {
            usuarioService.registrarCliente(form);
        } catch (ReglaNegocioException e) {
            model.addAttribute("errorNegocio", e.getMessage());
            return "registro";
        }
        return "redirect:/registro?exito";
    }
    @GetMapping("/confirmar")
    public String confirmar(@RequestParam String token, Model model) {
    try {
        usuarioService.confirmarCuenta(token);
        model.addAttribute("ok", true);
        model.addAttribute("mensaje", "Cuenta activada. Ya puedes iniciar sesión.");
    } catch (ReglaNegocioException e) {
        model.addAttribute("ok", false);
        model.addAttribute("mensaje", e.getMessage());
    }
    return "confirmacion";
}
}