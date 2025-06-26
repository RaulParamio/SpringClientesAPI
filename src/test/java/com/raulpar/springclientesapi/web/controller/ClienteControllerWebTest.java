package com.raulpar.springclientesapi.web.controller;

import com.raulpar.springclientesapi.controller.ClienteController;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Test unitario aislado para ClienteController utilizando MockMvc
@WebMvcTest(ClienteController.class)
// Se carga una configuración de prueba con un bean mockeado de ClienteService
@ContextConfiguration(classes = {ClienteController.class, ClienteControllerWebTest.TestConfig.class})
class ClienteControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteService clienteService;

    // Inicializa los mocks antes de cada test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Configuración de Spring para registrar un mock de ClienteService
    @Configuration
    static class TestConfig {
        @Bean
        public ClienteService clienteService() {
            return org.mockito.Mockito.mock(ClienteService.class);
        }
    }

    @Test
    void testGetAll() throws Exception {
        // Datos de prueba
        Cliente c1 = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        Cliente c2 = new Cliente("87654321B", "Lucía", "Gómez", "lucia@example.com", "Avenida Palacios", "Sevilla", "Sevilla");

        // Mock del servicio: devuelve una lista con dos clientes
        when(clienteService.findAll()).thenReturn(Arrays.asList(c1, c2));

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
        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        cliente.setIdCliente(1L);

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
        // Mock del servicio: cliente existe y es eliminado
        when(clienteService.deleteById(1L)).thenReturn(true);

        // Petición DELETE y verificación de 200 OK
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        // Mock del servicio: cliente no existe
        when(clienteService.deleteById(1L)).thenReturn(false);

        // Petición DELETE y verificación de 404 Not Found
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNotFound());
    }

}