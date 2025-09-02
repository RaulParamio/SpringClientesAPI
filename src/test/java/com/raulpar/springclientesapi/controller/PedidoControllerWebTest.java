
package com.raulpar.springclientesapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raulpar.springclientesapi.dto.PedidoCreateDto;
import com.raulpar.springclientesapi.dto.PedidoDto;
import com.raulpar.springclientesapi.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Test unitario aislado para PedidoController utilizando MockMvc
@WebMvcTest(PedidoController.class)
class PedidoControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoService pedidoService;


    // Test que comprueba que se devuelve correctamente una lista con todos los pedidos
    @Test
    void testGetAll() throws Exception {
        PedidoDto p1 = new PedidoDto();
        PedidoDto p2 = new PedidoDto();

        Mockito.when(pedidoService.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    // Test que comprueba la obtención de un pedido existente por ID
    @Test
    void testGetByIdFound() throws Exception {
        PedidoDto pedido = new PedidoDto();
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

        PedidoCreateDto pedido = new PedidoCreateDto();
        pedido.setIdCliente(18L);

        PedidoDto pedidoResponse = new PedidoDto();
        pedidoResponse.setIdCliente(18L);
        // Mock del servicio
        Mockito.when(pedidoService.save(any(PedidoCreateDto.class))).thenReturn(pedidoResponse);

        mockMvc.perform(post("/api/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCliente", is(18)));
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
        PedidoDto pedidoDto = new PedidoDto();

        List<PedidoDto> lista = List.of(pedidoDto);

        Mockito.when(pedidoService.findByFecha(fecha)).thenReturn(lista);

        mockMvc.perform(get("/api/pedido/fecha")
                        .param("fecha", fecha.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }
}