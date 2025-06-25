package unit.service;

import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import com.raulpar.springclientesapi.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
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

    // Crea una instancia real de PedidoService e inyecta el mock de pedidoRepository
    @InjectMocks
    private PedidoService pedidoService;

    // Inicializa los mocks antes de cada test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test que verifica que findAll devuelve una lista de 2 pedidos
    @Test
    void testFindAll() {
        List<Pedido> pedidosMock = Arrays.asList(new Pedido(), new Pedido());
        when(pedidoRepository.findAll()).thenReturn(pedidosMock);

        List<Pedido> pedidos = pedidoService.findAll();

        assertEquals(2, pedidos.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    // Test que verifica el comportamiento al buscar un pedido por su ID
    @Test
    void testFindById() {
        Pedido pedido = new Pedido();
        pedido.setNumPedido(1L);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getNumPedido());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    // Test para comprobar que el metodo save funciona correctamente
    @Test
    void testSave() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido guardado = pedidoService.save(pedido);

        assertNotNull(guardado);
        verify(pedidoRepository, times(1)).save(pedido);
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

        when(pedidoRepository.findByFechaBetween(desde, hasta)).thenReturn(pedidosMock);

        List<Pedido> resultado = pedidoService.findByFecha(fecha);

        assertEquals(2, resultado.size());
        verify(pedidoRepository, times(1)).findByFechaBetween(desde, hasta);
    }
}
