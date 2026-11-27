package com.manoamano.mano_a_mano.pagos.controller;

import com.manoamano.mano_a_mano.model.EstadoPago;
import com.manoamano.mano_a_mano.model.TransaccionPago;
import com.manoamano.mano_a_mano.pagos.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public record RetenerPagoRequest(
            Long servicioId,
            Long clienteId,
            Long tecnicoId,
            BigDecimal montoTotal,
            String referenciaPasarela
    ) {}

    @PostMapping("/retener")
    public ResponseEntity<TransaccionPago> retenerPago(@RequestBody RetenerPagoRequest request) {
        TransaccionPago creada = pagoService.retenerPago(
                request.servicioId(),
                request.clienteId(),
                request.tecnicoId(),
                request.montoTotal(),
                request.referenciaPasarela()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<TransaccionPago> liberarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.liberarPago(id));
    }

    @PostMapping("/{id}/reembolsar")
    public ResponseEntity<TransaccionPago> reembolsarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.reembolsarPago(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionPago> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarPorId(id));
    }

    @GetMapping("/servicio/{servicioId}")
    public ResponseEntity<TransaccionPago> obtenerPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(pagoService.buscarPorServicioId(servicioId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<TransaccionPago>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagoService.listarPorCliente(clienteId));
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<TransaccionPago>> listarPorTecnico(@PathVariable Long tecnicoId) {
        return ResponseEntity.ok(pagoService.listarPorTecnico(tecnicoId));
    }

    @GetMapping
    public ResponseEntity<List<TransaccionPago>> listarPorEstado(@RequestParam EstadoPago estado) {
        return ResponseEntity.ok(pagoService.listarPorEstado(estado));
    }
}
