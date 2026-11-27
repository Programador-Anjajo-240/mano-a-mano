package com.manoamano.mano_a_mano.contratacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manoamano.mano_a_mano.contratacion.dto.CotizacionForm;
import com.manoamano.mano_a_mano.contratacion.service.ContratacionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cotizaciones")
public class CotizacionController {

    private final ContratacionService contratacionService;

    public CotizacionController(ContratacionService contratacionService) {
        this.contratacionService = contratacionService;
    }

    @GetMapping("/{id}/responder")
    public String formulario(@PathVariable Long id, Model model) {
        model.addAttribute("cotizacionId", id);
        model.addAttribute("cotizacionForm", new CotizacionForm());
        return "contratacion/cotizacion-form";
    }

    @PostMapping("/{id}/responder")
    public String responder(@PathVariable Long id,
                            @Valid @ModelAttribute("cotizacionForm") CotizacionForm form,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cotizacionId", id);
            return "contratacion/cotizacion-form";
        }
        Long servicioId = contratacionService.responderCotizacion(id, form);
        return "redirect:/servicios/" + servicioId;
    }

    @PostMapping("/{id}/aceptar")
    public String aceptar(@PathVariable Long id) {
        Long servicioId = contratacionService.aceptarCotizacion(id);
        return "redirect:/servicios/" + servicioId;
    }

    @PostMapping("/{id}/rechazar")
    public String rechazar(@PathVariable Long id) {
        Long servicioId = contratacionService.rechazarCotizacion(id);
        return "redirect:/servicios/" + servicioId;
    }
}