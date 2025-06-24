package com.raulpar.springclientesapi.controller;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteservice;


    @Operation(summary = "Obtener todos los clientes / Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente / Customer list retrieved successfully")
    })
    @GetMapping
    public List<Cliente> getAll() {
        return clienteservice.findAll();
    }


    @Operation(summary = "Obtener cliente por ID / Get customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado / Customer found"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return clienteservice.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Guardar Cliente / Save customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente guardado correctamente / Customer saved succesfully")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteservice.save(cliente);
    }

    @Operation(summary = "Borrar Cliente por ID / Delete customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente borrado correctamente / Customer deleted succesfully"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = clienteservice.deleteById(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener Cliente por DNI / Get customer by DNI (National ID)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado correctamente / Customer found succesfully"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Customer not found")
    })
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Cliente> getByDni(@PathVariable String dni) {
        return clienteservice.findByDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}