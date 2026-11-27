package com.manoamano.mano_a_mano.contratacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ServicioForm {

    @NotBlank(message = "Describe el problema que necesitas resolver")
    @Size(max = 1000, message = "La descripción admite máximo 1000 caracteres")
    private String descripcion;

    @NotBlank(message = "Indica dónde se realizará el servicio")
    @Size(max = 255, message = "La ubicación admite máximo 255 caracteres")
    private String ubicacion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}