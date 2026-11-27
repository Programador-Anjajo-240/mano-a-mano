package com.manoamano.mano_a_mano.contratacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manoamano.mano_a_mano.contratacion.dto.ServicioForm;
import com.manoamano.mano_a_mano.contratacion.model.ServicioSolicitado;
import com.manoamano.mano_a_mano.contratacion.service.ContratacionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ContratacionService contratacionService;

    public ServicioController(ContratacionService contratacionService) {
        this.contratacionService = contratacionService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicioForm", new ServicioForm());
        return "contratacion/servicio-form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("servicioForm") ServicioForm form,
                        BindingResult result) {
        if (result.hasErrors()) {
            return "contratacion/servicio-form";
        }
        ServicioSolicitado servicio = contratacionService.crearServicio(form);
        return "redirect:/servicios/" + servicio.getId();
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("servicio", contratacionService.buscarPorId(id));
        return "contratacion/servicio-detalle";
    }
}