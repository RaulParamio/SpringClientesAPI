package com.raulpar.springclientesapi.integration.service;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import com.raulpar.springclientesapi.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PedidoServiceIntTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    //Borrado antes de cada test para que no se afecten entre ellos
    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();

        // Crear cliente por defecto para usar en todos los tests
        cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Falsa", "Madrid", "Madrid");
        cliente = clienteRepository.save(cliente);
    }

    // Test que guarda un pedido y lo busca por su ID para verificar que fue correctamente persistido.
    @Test
    void testSaveAndFindById() {
        Pedido pedido = new Pedido(cliente);
        pedido.setFecha(LocalDate.now().atStartOfDay());

        Pedido saved = pedidoService.save(pedido);

        Optional<Pedido> result = pedidoService.findById(saved.getNumPedido());
        assertTrue(result.isPresent());
        assertEquals(saved.getNumPedido(), result.get().getNumPedido());
    }

    // Test que guarda dos pedidos y comprueba que el metodo findAll devuelve esos dos pedidos.
    @Test
    void testFindAll() {
        Pedido p1 = new Pedido(cliente);
        Pedido p2 = new Pedido(cliente);
        p1.setFecha(LocalDate.now().atStartOfDay());
        p2.setFecha(LocalDate.now().atStartOfDay());

        pedidoService.save(p1);
        pedidoService.save(p2);

        List<Pedido> pedidos = pedidoService.findAll();
        assertEquals(2, pedidos.size());
    }

    // Test que comprueba que un pedido puede eliminarse correctamente por su ID.
    @Test
    void testDeleteById() {
        Pedido pedido = new Pedido(cliente);
        pedido.setFecha(LocalDate.now().atStartOfDay());

        Pedido saved = pedidoService.save(pedido);

        boolean deleted = pedidoService.deleteById(saved.getNumPedido());
        assertTrue(deleted);

        Optional<Pedido> result = pedidoService.findById(saved.getNumPedido());
        assertFalse(result.isPresent());
    }

    // Test que verifica que el metodo findByFecha devuelve correctamente los pedidos hechos en un día específico.
    @Test
    void testFindByFecha() {
        LocalDate fecha = LocalDate.now();

        // Crear pedido con la fecha buscada
        Pedido pedido = new Pedido(cliente);
        pedidoService.save(pedido);

        // Ejecutar búsqueda
        List<Pedido> resultados = pedidoService.findByFecha(fecha);
        resultados.forEach(p -> System.out.println("Pedido: " + p.getFecha()));
        // Verificar que el pedido fue encontrado
        assertEquals(1, resultados.size());
    }

    // Test negativo: se consulta una fecha en la que no hay pedidos y se espera que la lista esté vacía.
    @Test
    void testFindByFechaNoResults() {
        LocalDate fecha = LocalDate.of(2025, 4, 19);

        List<Pedido> resultados = pedidoService.findByFecha(fecha);
        assertTrue(resultados.isEmpty());
    }
}