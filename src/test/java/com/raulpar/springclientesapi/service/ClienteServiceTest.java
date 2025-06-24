package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ClienteServiceTest {


    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;


    //Borrado antes de cada test para que no se afecten entre ellos
    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    //Tests con datos introducidos en el propio test para que siempre puedan funcionar
    @Test
    void testFindById() {
        //Creacion y guardado de cliente
        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        Cliente guardado = clienteService.save(cliente);

        //Optional -> para evitar error NullPointerException , y usamos el metodo para probarlo.
        Optional<Cliente> encontrado = clienteService.findById(guardado.getIdCliente());

        //Comprobamos que exista, y que se llame con el nombre del cliente que buscamos
        assertTrue(encontrado.isPresent());
        assertEquals("Juan", encontrado.get().getNombre());
    }

    @Test
    void testFindByDni() {
        Cliente cliente = new Cliente("87654321B", "Lucía", "Gómez", "lucia@example.com", "Avenida Palacios", "Sevilla", "Sevilla");
        clienteService.save(cliente);

        Optional<Cliente> encontrado = clienteService.findByDni("87654321B");

        assertTrue(encontrado.isPresent());
        assertEquals("Lucía", encontrado.get().getNombre());
    }

    @Test
    void testDeleteById() {
        Cliente cliente = new Cliente("99999999C", "Carlos", "Ruiz", "carlos@example.com", "Calle Segovia", "Valencia", "Valencia");
        Cliente guardado = clienteService.save(cliente);

        clienteService.deleteById(guardado.getIdCliente());

        Optional<Cliente> encontrado = clienteService.findById(guardado.getIdCliente());
        assertFalse(encontrado.isPresent());
    }

    @Test
    void testFindAll() {
        // Crear y guardar varios clientes de prueba
        Cliente cliente1 = new Cliente("11111111A", "Ana", "Lopez", "ana@example.com", "Calle 1", "Ciudad", "Provincia");
        Cliente cliente2 = new Cliente("22222222B", "Luis", "Garcia", "luis@example.com", "Calle 2", "Ciudad", "Provincia");
        clienteService.save(cliente1);
        clienteService.save(cliente2);

        // Ejecutar el metodo findall
        List<Cliente> clientes = clienteService.findAll();

        // Comprobar que devuelve los clientes guardados
        assertEquals(2, clientes.size());
        assertTrue(clientes.stream().anyMatch(c -> c.getDni().equals("11111111A")));
        assertTrue(clientes.stream().anyMatch(c -> c.getDni().equals("22222222B")));
    }

}
