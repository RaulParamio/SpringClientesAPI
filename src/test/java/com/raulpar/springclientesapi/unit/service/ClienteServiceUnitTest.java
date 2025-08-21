package com.raulpar.springclientesapi.unit.service;

import com.raulpar.springclientesapi.dto.ClienteInputDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDetailDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDto;
import com.raulpar.springclientesapi.mapper.ClienteMapper;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import com.raulpar.springclientesapi.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para {@link ClienteService}.
 * Verifica operaciones como guardar, buscar, eliminar y listar clientes.
 */
class ClienteServiceUnitTest {

    // Simula (mockea) el comportamiento del repositorio para no tener que acceder a la base de datos real
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

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

        ClienteOutputDto dto1 = new ClienteOutputDto(1L, "12345678A", "Juan", "Pérez", "juan12@gmail.com");
        ClienteOutputDto dto2 = new ClienteOutputDto(2L, "87654321B", "Lucía", "Gómez", "lucia47@gmail.com");

        // Cuando se llama al metodo findAll del repositorio, devolvemos los datos simulados
        when(clienteRepository.findAll()).thenReturn(clientesMock);
        when(clienteMapper.toOutputList(clientesMock)).thenReturn(Arrays.asList(dto1, dto2));

        // Ejecutamos el metodo real del servicio
        List<ClienteOutputDto> clientes = clienteService.findAll();

        //Comprobacion del resultado
        assertEquals(2, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombre());
        verify(clienteMapper, times(1)).toOutputList(clientesMock);
    }

    // Test que comprueba que findById devuelve el cliente correcto
    @Test
    void testFindById() {
        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@gmail.com", "Calle Mendez", "Madrid", "Madrid");
        cliente.setIdCliente(1L);

        ClienteOutputDetailDto dto = new ClienteOutputDetailDto(
                1L, "12345678A", "Juan", "Pérez", "juan@gmail.com", "Calle Mendez", "Madrid", "Madrid");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDetailDto(cliente)).thenReturn(dto);

        Optional<ClienteOutputDetailDto> resultado = clienteService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteMapper).toDetailDto(cliente);
    }

    // Test para comprobar que se guarda correctamente un cliente
    @Test
    void testSave() {
        ClienteInputDto input = new ClienteInputDto("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");

        Cliente clienteEntity = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        Cliente clienteGuardado = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        clienteGuardado.setIdCliente(1L);

        ClienteOutputDetailDto outputDto = new ClienteOutputDetailDto(
                1L, "12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");

        when(clienteMapper.toEntity(input)).thenReturn(clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteGuardado);
        when(clienteMapper.toDetailDto(clienteGuardado)).thenReturn(outputDto);

        ClienteOutputDetailDto resultado = clienteService.save(input);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteMapper).toEntity(input);
        verify(clienteRepository).save(clienteEntity);
        verify(clienteMapper).toDetailDto(clienteGuardado);
    }

    // Test que verifica que se elimina un cliente si existe
    @Test
    void testDeleteById_Exists() {
        Long id = 1L;

        Cliente cliente = new Cliente("12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        cliente.setIdCliente(id);

        ClienteOutputDetailDto dto = new ClienteOutputDetailDto(
                id, "12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid"
        );

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(id);
        when(clienteMapper.toDetailDto(cliente)).thenReturn(dto);

        Optional<ClienteOutputDetailDto> resultado = clienteService.deleteById(id);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).deleteById(id);
        verify(clienteMapper, times(1)).toDetailDto(cliente);
    }


    // Test que comprueba que no se intenta eliminar si el cliente no existe
    @Test
    void testDeleteById_NotExists() {
        Long id = 2L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ClienteOutputDetailDto> resultado = clienteService.deleteById(id);

        assertFalse(resultado.isPresent());
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).deleteById(anyLong());
        verify(clienteMapper, never()).toDetailDto(any());
    }


    // Test que verifica que se puede buscar un cliente por su DNI
    @Test
    void testFindByDni() {
        Cliente cliente = new Cliente(
                "12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid");
        ClienteOutputDetailDto dtoEsperado = new ClienteOutputDetailDto(
                1L, "12345678A", "Juan", "Pérez", "juan@example.com", "Calle Mendez", "Madrid", "Madrid"
        );

        when(clienteRepository.findByDni("12345678A")).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDetailDto(cliente)).thenReturn(dtoEsperado);

        Optional<ClienteOutputDetailDto> resultado = clienteService.findByDni("12345678A");

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findByDni("12345678A");
        verify(clienteMapper, times(1)).toDetailDto(cliente);
    }
}
