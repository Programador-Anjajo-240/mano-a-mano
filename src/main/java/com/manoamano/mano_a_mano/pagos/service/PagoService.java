package com.manoamano.mano_a_mano.pagos.service;

import com.manoamano.mano_a_mano.model.EstadoPago;
import com.manoamano.mano_a_mano.model.TransaccionPago;
import com.manoamano.mano_a_mano.repository.TransaccionPagoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PagoService {

    private static final BigDecimal TASA_COMISION = new BigDecimal("0.10");

    private final TransaccionPagoRepository transaccionPagoRepository;

    public PagoService(TransaccionPagoRepository transaccionPagoRepository) {
        this.transaccionPagoRepository = transaccionPagoRepository;
    }

    @Transactional
    public TransaccionPago retenerPago(Long servicioId, Long clienteId, Long tecnicoId,
                                      BigDecimal montoTotal, String referenciaPasarela) {
        if (servicioId == null || clienteId == null || tecnicoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "servicioId, clienteId y tecnicoId son obligatorios");
        }
        if (montoTotal == null || montoTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El montoTotal debe ser mayor a cero");
        }
        if (transaccionPagoRepository.findByServicioId(servicioId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una transaccion de pago registrada para el servicio " + servicioId);
        }

        BigDecimal montoNormalizado = montoTotal.setScale(2, RoundingMode.HALF_UP);
        BigDecimal comision = calcularComision(montoNormalizado);
        BigDecimal netoTecnico = montoNormalizado.subtract(comision).setScale(2, RoundingMode.HALF_UP);

        TransaccionPago tx = new TransaccionPago();
        tx.setServicioId(servicioId);
        tx.setClienteId(clienteId);
        tx.setTecnicoId(tecnicoId);
        tx.setMontoTotal(montoNormalizado);
        tx.setComisionPlataforma(comision);
        tx.setMontoTecnico(netoTecnico);
        tx.setEstado(EstadoPago.RETENIDO);
        tx.setReferenciaPasarela(referenciaPasarela);

        return transaccionPagoRepository.save(tx);
    }

    @Transactional
    public TransaccionPago liberarPago(Long transaccionId) {
        TransaccionPago tx = buscarPorId(transaccionId);

        if (tx.getEstado() != EstadoPago.RETENIDO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Solo se pueden liberar pagos en estado RETENIDO. Estado actual: " + tx.getEstado());
        }

        BigDecimal montoNormalizado = tx.getMontoTotal().setScale(2, RoundingMode.HALF_UP);
        BigDecimal comision = calcularComision(montoNormalizado);
        BigDecimal netoTecnico = montoNormalizado.subtract(comision).setScale(2, RoundingMode.HALF_UP);

        tx.setMontoTotal(montoNormalizado);
        tx.setComisionPlataforma(comision);
        tx.setMontoTecnico(netoTecnico);
        tx.setEstado(EstadoPago.LIBERADO);

        return transaccionPagoRepository.save(tx);
    }

    @Transactional
    public TransaccionPago reembolsarPago(Long transaccionId) {
        TransaccionPago tx = buscarPorId(transaccionId);

        if (tx.getEstado() != EstadoPago.RETENIDO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Solo se pueden reembolsar pagos en estado RETENIDO. Estado actual: " + tx.getEstado());
        }

        tx.setEstado(EstadoPago.REEMBOLSADO);
        return transaccionPagoRepository.save(tx);
    }

    @Transactional(readOnly = true)
    public TransaccionPago buscarPorId(Long transaccionId) {
        return transaccionPagoRepository.findById(transaccionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Transaccion de pago no encontrada con id: " + transaccionId));
    }

    @Transactional(readOnly = true)
    public TransaccionPago buscarPorServicioId(Long servicioId) {
        return transaccionPagoRepository.findByServicioId(servicioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe transaccion de pago para el servicio: " + servicioId));
    }

    @Transactional(readOnly = true)
    public List<TransaccionPago> listarPorCliente(Long clienteId) {
        return transaccionPagoRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<TransaccionPago> listarPorTecnico(Long tecnicoId) {
        return transaccionPagoRepository.findByTecnicoId(tecnicoId);
    }

    @Transactional(readOnly = true)
    public List<TransaccionPago> listarPorEstado(EstadoPago estado) {
        return transaccionPagoRepository.findByEstado(estado);
    }

    private BigDecimal calcularComision(BigDecimal montoTotal) {
        return montoTotal.multiply(TASA_COMISION).setScale(2, RoundingMode.HALF_UP);
    }
}
