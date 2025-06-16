package com.raulpar.springclientesapi.repository;

import com.raulpar.springclientesapi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}

