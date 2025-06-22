package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los pedidos.
 */
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    /**
     * Obtiene la lista de pedidos realizados en una fecha específica.
     * El rango de búsqueda es desde el inicio hasta el final del día indicado.
     * Se pone automaticamente la hora a la que inicia el dia y acaba ya que buscamos sacar los pedidos del dia
     * sin necesidad de buscar la hora, es necesario poner horas ya que la base de datos funciona con DateTime
     *
     * @param fecha Fecha para filtrar los pedidos (solo día, sin hora).
     * @return Lista de pedidos realizados en la fecha especificada.
     */
    public List<Pedido> findByFecha(LocalDate fecha) {
        LocalDateTime desde = fecha.atStartOfDay(); // 2025-04-19T00:00:00
        LocalDateTime hasta = fecha.atTime(LocalTime.MAX); // 2025-04-19T23:59:59.999999999
        return pedidoRepository.findByFechaBetween(desde, hasta);
    }
}

