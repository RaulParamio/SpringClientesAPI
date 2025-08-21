package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.dto.PedidoDto;
import com.raulpar.springclientesapi.mapper.PedidoMapper;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los pedidos.
 */
@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ClienteRepository clienteRepository;

    /**
     * Recupera todos los pedidos almacenados.
     */
    public List<PedidoDto> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toDtoList(pedidos);
    }

    /**
     * Busca un pedido por su ID.
     *
     * @param id Identificador del pedido
     * @return Pedido encontrado, si existe
     */
    public Optional<PedidoDto> findById(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDto);
    }

    /**
     * Guarda un nuevo pedido o actualiza uno existente.
     *
     * @param pedidoDto Pedido a guardar
     * @return Pedido guardado
     */
    public PedidoDto save(PedidoDto pedidoDto) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDto);

        Cliente cliente = clienteRepository.findById(pedidoDto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        pedido.setCliente(cliente);

        Pedido pedidosaved = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedidosaved);
    }

    /**
     * Elimina un pedido por ID, si existe.
     *
     * @param id ID del pedido
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean deleteById(Long id) {
        if(pedidoRepository.existsById(id)){
            pedidoRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Devuelve los pedidos realizados en una fecha específica.
     *
     * El rango de búsqueda va desde las 00:00 hasta las 23:59 del día indicado,
     * ya que la base de datos almacena la fecha como DateTime y es necesario
     * incluir el día completo para obtener resultados precisos.
     *
     * @param fecha Día por el que se filtrarán los pedidos (sin hora).
     * @return Lista de pedidos realizados en esa fecha.
     */
    public List<PedidoDto> findByFecha(LocalDate fecha) {
        LocalDateTime desde = fecha.atStartOfDay(); // 2025-04-19T00:00:00
        LocalDateTime hasta = fecha.atTime(LocalTime.MAX); // 2025-04-19T23:59:59.999999999
        List<Pedido> pedido =  pedidoRepository.findByFechaBetween(desde, hasta);
        return pedidoMapper.toDtoList(pedido);
    }

}

