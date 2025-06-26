
package com.raulpar.springclientesapi.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raulpar.springclientesapi.controller.PedidoController;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Test unitario aislado para PedidoController utilizando MockMvc
@WebMvcTest(PedidoController.class)
// Se carga una configuración de prueba personalizada con un bean mockeado de PedidoService
@ContextConfiguration(classes = {PedidoController.class, PedidoControllerWebTest.TestConfig.class})
class PedidoControllerWebTest {

    // Inyección del objeto MockMvc para realizar llamadas HTTP simuladas al controlador
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoService;

    // Utilizado para convertir objetos Java a JSON
    @Autowired
    private ObjectMapper objectMapper;

    // Se ejecuta antes de cada test para inicializar los mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Clase de configuración interna para registrar el mock de PedidoService como bean
    @Configuration
    static class TestConfig {
        @Bean
        public PedidoService pedidoService() {
            return org.mockito.Mockito.mock(PedidoService.class);
        }
    }

    // Cliente de prueba usado para construir pedidos ficticios
    private final Cliente cliente = new Cliente(
            "12345678A", "Juan", "Pérez", "juan@gmail.com", "Calle 1", "Madrid", "Madrid"
    );

    // Test que comprueba que se devuelve correctamente una lista con todos los pedidos
    @Test
    void testGetAll() throws Exception {
        Pedido p1 = new Pedido(cliente);
        Pedido p2 = new Pedido(cliente);

        Mockito.when(pedidoService.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    // Test que comprueba la obtención de un pedido existente por ID
    @Test
    void testGetByIdFound() throws Exception {
        Pedido pedido = new Pedido(cliente);
        pedido.setNumPedido(1L);

        Mockito.when(pedidoService.findById(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/api/pedido/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numPedido", is(1)));
    }

    // Test que comprueba la respuesta al intentar obtener un pedido inexistente
    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(pedidoService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pedido/99"))
                .andExpect(status().isNotFound());
    }

    // Test que simula la creación de un pedido vía POST y comprueba la respuesta
    @Test
    void testCreatePedido() throws Exception {
        Pedido pedido = new Pedido(cliente);
        pedido.setFecha(LocalDateTime.now());

        Mockito.when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cliente.dni", is("12345678A")));
    }

    // Test que verifica la eliminación exitosa de un pedido existente
    @Test
    void testDeletePedidoFound() throws Exception {
        Mockito.when(pedidoService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/pedido/1"))
                .andExpect(status().isOk());
    }

    // Test que verifica la respuesta cuando se intenta eliminar un pedido inexistente
    @Test
    void testDeletePedidoNotFound() throws Exception {
        Mockito.when(pedidoService.deleteById(2L)).thenReturn(false);

        mockMvc.perform(delete("/api/pedido/2"))
                .andExpect(status().isNotFound());
    }

    // Test que comprueba la obtención de pedidos por fecha específica
    @Test
    void testGetByFecha() throws Exception {
        LocalDate fecha = LocalDate.now();
        Pedido pedido = new Pedido(cliente);
        pedido.setFecha(fecha.atStartOfDay());

        List<Pedido> lista = List.of(pedido);

        Mockito.when(pedidoService.findByFecha(fecha)).thenReturn(lista);

        mockMvc.perform(get("/api/pedido/fecha")
                        .param("fecha", fecha.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }
}