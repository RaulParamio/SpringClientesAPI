package com.raulpar.springclientesapi.controller;

import com.raulpar.springclientesapi.dto.PedidoDto;
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


    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found"),
    })
    @GetMapping
    public List<PedidoDto> getAll() {
        return pedidoService.findAll();
    }

    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getById(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto create(@RequestBody PedidoDto pedidoDto) {
        return pedidoService.save(pedidoDto);
    }


    @Operation(summary = "Delete order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted"),
            @ApiResponse(responseCode = "404", description = "Order not found")
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

    @Operation(summary = "Get orders by date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found"),
    })
    @GetMapping("/fecha")
    public ResponseEntity<List<PedidoDto>> getByFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<PedidoDto> pedidos = pedidoService.findByFecha(fecha);
        return ResponseEntity.ok(pedidos);
    }

}
