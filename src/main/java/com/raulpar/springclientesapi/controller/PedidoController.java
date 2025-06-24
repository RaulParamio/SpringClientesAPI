package com.raulpar.springclientesapi.controller;

import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;


    @Operation(summary = "Obtener todos los pedidos / Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados / Orders found"),
    })
    @GetMapping
    public List<Pedido> getAll() {
        return pedidoService.findAll();
    }

    @Operation(summary = "Obtener pedido por ID / Get order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado / Order found"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado / Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear pedido / Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado / Order created successfully")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido create(@RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    @Operation(summary = "Borrar pedido por ID / Delete order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido borrado / Order deleted"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado / Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = pedidoService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener pedidos por fecha / Get orders by date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados / Orders found"),
    })
    @GetMapping("/fecha")
    public ResponseEntity<List<Pedido>> getByFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Pedido> pedidos = pedidoService.findByFecha(fecha);
        return ResponseEntity.ok(pedidos);
    }

}
