package com.raulpar.springclientesapi.service.unit;

import com.raulpar.springclientesapi.dto.PedidoCreateDto;
import com.raulpar.springclientesapi.dto.PedidoDto;
import com.raulpar.springclientesapi.mapper.PedidoMapper;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import com.raulpar.springclientesapi.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceUnitTest {

    // Simula (mockea) el comportamiento del repositorio para no tener que acceder a la base de datos real
    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    private AutoCloseable mocks;

    // Crea una instancia real de PedidoService e inyecta el mock de pedidoRepository
    @InjectMocks
    private PedidoService pedidoService;

    // Inicializa los mocks antes de cada test
    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    // Test que verifica que findAll devuelve una lista de 2 pedidos
    @Test
    void testFindAll() {
        List<Pedido> pedidosMock = Arrays.asList(new Pedido(), new Pedido());
        List<PedidoDto> pedidosDto = Arrays.asList(new PedidoDto(), new PedidoDto());
        when(pedidoRepository.findAll()).thenReturn(pedidosMock);
        when(pedidoMapper.toDtoList(pedidosMock)).thenReturn(pedidosDto);

        List<PedidoDto> pedidos = pedidoService.findAll();

        assertEquals(2, pedidos.size());
        verify(pedidoRepository, times(1)).findAll();
        verify(pedidoMapper, times(1)).toDtoList(pedidosMock);
    }

    // Test que verifica el comportamiento al buscar un pedido por su ID
    @Test
    void testFindById() {
        Pedido pedido = new Pedido();
        pedido.setNumPedido(1L);
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setNumPedido(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDto(pedido)).thenReturn(pedidoDto);

        Optional<PedidoDto> resultado = pedidoService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getNumPedido());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoMapper, times(1)).toDto(pedido);
    }

    // Test para comprobar que el metodo save funciona correctamente
    @Test
    void testSave() {
        PedidoCreateDto pedidoCreateDto = new PedidoCreateDto();
        pedidoCreateDto.setIdCliente(18L);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(18L);

        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setNumPedido(1L);

        PedidoDto savedDto = new PedidoDto();
        savedDto.setNumPedido(1L);

        // Mock: devolver el cliente cuando se busca por ID
        when(clienteRepository.findById(18L)).thenReturn(Optional.of(cliente));


        // Mock: cuando el repositorio guarda, devuelve la entidad
        when(pedidoMapper.toEntity(pedidoCreateDto)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoEntity);
        when(pedidoMapper.toDto(pedidoEntity)).thenReturn(savedDto);

        // Llamar al servicio (que convierte DTO → entidad)
        PedidoDto guardado = pedidoService.save(pedidoCreateDto);

        assertNotNull(guardado);
        assertEquals(1L, guardado.getNumPedido());
        verify(pedidoMapper, times(1)).toEntity(pedidoCreateDto);
        verify(pedidoRepository, times(1)).save(pedidoEntity);
        verify(pedidoMapper, times(1)).toDto(pedidoEntity);
    }

    // Test para verificar que deleteById elimina un pedido si existe
    @Test
    void testDeleteById_Exists() {

        when(pedidoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(1L);

        boolean eliminado = pedidoService.deleteById(1L);

        assertTrue(eliminado);
        verify(pedidoRepository, times(1)).existsById(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    // Test para verificar que deleteById devuelve false si el pedido no existe
    @Test
    void testDeleteById_NotExists() {
        when(pedidoRepository.existsById(1L)).thenReturn(false);

        boolean eliminado = pedidoService.deleteById(1L);

        assertFalse(eliminado);
        verify(pedidoRepository, times(1)).existsById(1L);
        verify(pedidoRepository, never()).deleteById(anyLong());
    }

    // Test para comprobar que se filtran los pedidos por una fecha dada
    @Test
    void testFindByFecha() {
        LocalDate fecha = LocalDate.of(2025, 4, 19);
        LocalDateTime desde = fecha.atStartOfDay();
        LocalDateTime hasta = fecha.atTime(LocalTime.MAX);

        List<Pedido> pedidosMock = Arrays.asList(new Pedido(), new Pedido());
        List<PedidoDto> dtoMock = Arrays.asList(new PedidoDto(), new PedidoDto());

        when(pedidoRepository.findByFechaBetween(desde, hasta)).thenReturn(pedidosMock);
        when(pedidoMapper.toDtoList(pedidosMock)).thenReturn(dtoMock);

        List<PedidoDto> resultado = pedidoService.findByFecha(fecha);

        assertEquals(2, resultado.size());
        verify(pedidoRepository, times(1)).findByFechaBetween(desde, hasta);
        verify(pedidoMapper, times(1)).toDtoList(pedidosMock);
    }
}
