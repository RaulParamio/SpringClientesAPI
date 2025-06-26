package com.raulpar.springclientesapi.unit.service;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import com.raulpar.springclientesapi.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceUnitTest {

    // Simula (mockea) el comportamiento del repositorio para no tener que acceder a la base de datos real
    @Mock
    private ClienteRepository clienteRepository;

    // Crea una instancia real del servicio e inyecta el mock anterior
    @InjectMocks
    private ClienteService clienteService;

    // Inicializa los mocks antes de cada prueba
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test que comprueba que findAll devuelve la lista esperada
    @Test
    void testFindAll() {
        // Datos simulados
        List<Cliente> clientesMock = Arrays.asList(
                new Cliente("12345678A", "Juan", "Pérez", "juan12@gmail.com", "Calle Zaragoza", "Madrid", "Madrid"),
                new Cliente("87654321B", "Lucía", "Gómez", "lucia47@gmail.com", "Avenida Palacios", "Sevilla", "Sevilla")
        );
        when(clienteRepository.findAll()).thenReturn(clientesMock); // Cuando se llama al metodo findAll del repositorio, devolvemos los datos simulados

        List<Cliente> clientes = clienteService.findAll(); // Ejecutamos el metodo real del servicio

        //Comprobacion del resultado
        assertEquals(2, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    // Test que comprueba que findById devuelve el cliente correcto
    @Test
    void testFindById() {
        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        cliente.setIdCliente(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    // Test para comprobar que se guarda correctamente un cliente
    @Test
    void testSave() {
        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente guardado = clienteService.save(cliente);

        assertNotNull(guardado);
        assertEquals("Juan", guardado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    // Test que verifica que se elimina un cliente si existe
    @Test
    void testDeleteById_Exists() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(id);

        boolean resultado = clienteService.deleteById(id);

        assertTrue(resultado);
        verify(clienteRepository, times(1)).existsById(id);
        verify(clienteRepository, times(1)).deleteById(id);
    }

    // Test que comprueba que no se intenta eliminar si el cliente no existe
    @Test
    void testDeleteById_NotExists() {
        Long id = 2L;
        when(clienteRepository.existsById(id)).thenReturn(false);

        boolean resultado = clienteService.deleteById(id);

        assertFalse(resultado);
        verify(clienteRepository, times(1)).existsById(id);
        verify(clienteRepository, never()).deleteById(anyLong());
    }


    // Test que verifica que se puede buscar un cliente por su DNI
    @Test
    void testFindByDni() {
        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        when(clienteRepository.findByDni("12345678A")).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.findByDni("12345678A");

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findByDni("12345678A");
    }
}
