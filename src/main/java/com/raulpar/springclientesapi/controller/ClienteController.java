package com.raulpar.springclientesapi.controller;

import com.raulpar.springclientesapi.dto.ClienteInputDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDetailDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDto;
import com.raulpar.springclientesapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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


    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer list retrieved successfully")
    })
    @GetMapping
    public List<ClienteOutputDto> getAll() {
        return clienteservice.findAll();
    }


    @Operation(summary = "Get customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteOutputDetailDto> getById(@PathVariable Long id) {
        return clienteservice.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteOutputDetailDto> update(@PathVariable Long id, @RequestBody ClienteInputDto clienteDto) {
        return clienteservice.update(id, clienteDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Save customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer saved succesfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteOutputDetailDto> create(@Valid @RequestBody ClienteInputDto clienteinputdto) {
            ClienteOutputDetailDto cliente = clienteservice.save(clienteinputdto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        }

    @Operation(summary = "Delete customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted succesfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteOutputDetailDto> delete(@PathVariable Long id) {
        return clienteservice.deleteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Get customer by DNI (National ID)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found succesfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteOutputDetailDto> getByDni(@PathVariable String dni) {
        return clienteservice.findByDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}