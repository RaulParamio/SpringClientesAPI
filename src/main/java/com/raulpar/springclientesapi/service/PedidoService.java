package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los pedidos.
 */
@AllArgsConstructor
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    /**
     * Recupera todos los pedidos almacenados.
     */
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    /**
     * Busca un pedido por su ID.
     *
     * @param id Identificador del pedido
     * @return Pedido encontrado, si existe
     */
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Guarda un nuevo pedido o actualiza uno existente.
     *
     * @param pedido Pedido a guardar
     * @return Pedido guardado
     */
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
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
    public List<Pedido> findByFecha(LocalDate fecha) {
        LocalDateTime desde = fecha.atStartOfDay(); // 2025-04-19T00:00:00
        LocalDateTime hasta = fecha.atTime(LocalTime.MAX); // 2025-04-19T23:59:59.999999999
        return pedidoRepository.findByFechaBetween(desde, hasta);
    }
}

