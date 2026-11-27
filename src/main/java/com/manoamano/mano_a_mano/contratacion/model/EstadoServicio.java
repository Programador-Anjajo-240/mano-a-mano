package com.manoamano.mano_a_mano.contratacion.model;

public enum EstadoServicio {
    SOLICITADO,
    COTIZADO,
    CONTRATADO,
    PROGRAMADO,
    EN_EJECUCION,
    FINALIZADO,
    CANCELADO;

    public boolean puedePasarA(EstadoServicio destino) {
        return switch (this) {
            case SOLICITADO -> destino == COTIZADO || destino == CANCELADO;
            case COTIZADO -> destino == CONTRATADO || destino == CANCELADO;
            case CONTRATADO -> destino == PROGRAMADO || destino == CANCELADO;
            case PROGRAMADO -> destino == EN_EJECUCION || destino == CANCELADO;
            case EN_EJECUCION -> destino == FINALIZADO;
            case FINALIZADO, CANCELADO -> false;
        };
    }
}