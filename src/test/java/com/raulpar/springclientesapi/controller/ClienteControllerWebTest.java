package com.raulpar.springclientesapi.controller;

import com.raulpar.springclientesapi.dto.ClienteOutputDetailDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDto;
import com.raulpar.springclientesapi.mapper.ClienteMapper;
import com.raulpar.springclientesapi.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Test unitario aislado para ClienteController utilizando MockMvc
@WebMvcTest(ClienteController.class)
class ClienteControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private ClienteMapper clienteMapper;

    @Test
    void testGetAll() throws Exception {
        // Datos de prueba
        ClienteOutputDto c1 = new ClienteOutputDto(1L, "12345678A", "Juan", "Pérez", "juan@gmail.com");
        ClienteOutputDto c2 = new ClienteOutputDto(2L, "87654321B", "Lucía", "Gómez", "lucia@gmail.com");

        // Mock del servicio: devuelve una lista con dos clientes
        when(clienteService.findAll()).thenReturn(List.of(c1, c2));

        // Petición GET y verificación de que hay dos elementos y coinciden los DNIs
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].dni", is("12345678A")))
                .andExpect(jsonPath("$[1].dni", is("87654321B")));
    }

    @Test
    void testGetByIdFound() throws Exception {
        // Cliente de prueba con ID asignado
        ClienteOutputDetailDto cliente = new ClienteOutputDetailDto(1L, "12345678A", "Juan", "Pérez", "juan@gmail.com", "Calle Mendez", "Madrid", "Madrid");

        // Mock del servicio: cliente encontrado por ID
        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

        // Petición GET por ID y verificación de que se devuelve correctamente
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan")));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        // Mock del servicio: cliente no encontrado
        when(clienteService.findById(1L)).thenReturn(Optional.empty());

        // Petición GET por ID y verificación de 404 Not Found
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteFound() throws Exception {
        Long id = 1L;
        ClienteOutputDetailDto dto = new ClienteOutputDetailDto(1L, "12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");

        when(clienteService.deleteById(id)).thenReturn(Optional.of(dto));

        mockMvc.perform(delete("/api/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("12345678A"))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testDeleteNotFound() throws Exception {
        Long id = 2L;
        // Mock del servicio: cliente no existe
        when(clienteService.deleteById(id)).thenReturn(Optional.empty());

        // Petición DELETE y verificación de 404 Not Found
        mockMvc.perform(delete("/api/clientes/{id}", id))
                .andExpect(status().isNotFound());

        verify(clienteService).deleteById(id);
    }

}