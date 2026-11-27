package com.manoamano.mano_a_mano.contratacion.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CotizacionForm {

    @NotNull(message = "Indica el valor de la mano de obra")
    @DecimalMin(value = "0.0", message = "No puede ser negativo")
    private BigDecimal manoDeObra;

    @NotNull(message = "Indica el valor de los materiales")
    @DecimalMin(value = "0.0", message = "No puede ser negativo")
    private BigDecimal materiales;

    public BigDecimal getManoDeObra() {
        return manoDeObra;
    }

    public void setManoDeObra(BigDecimal manoDeObra) {
        this.manoDeObra = manoDeObra;
    }

    public BigDecimal getMateriales() {
        return materiales;
    }

    public void setMateriales(BigDecimal materiales) {
        this.materiales = materiales;
    }
}