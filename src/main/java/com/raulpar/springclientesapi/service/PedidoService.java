package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.model.Pedido;
import com.raulpar.springclientesapi.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    public List<Pedido> findByFecha(LocalDate fecha) {
        LocalDateTime desde = fecha.atStartOfDay(); // 2025-04-19T00:00:00
        LocalDateTime hasta = fecha.atTime(LocalTime.MAX); // 2025-04-19T23:59:59.999999999
        return pedidoRepository.findByFechaBetween(desde, hasta);
    }
}

