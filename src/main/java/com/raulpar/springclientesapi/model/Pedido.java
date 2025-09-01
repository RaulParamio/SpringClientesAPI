package com.raulpar.springclientesapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity that represents an order.")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numPedido")
    @Schema(description = "Order's Unique identifier.", example = "1")
    private Long numPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @Schema(description = "Customer's Unique identifier.", example = "1")
    private Cliente cliente;

    @Column(name = "fecha", updatable = false)
    @CreationTimestamp
    @Schema(description = "Order's Date.", example = "2025-04-19")
    private LocalDateTime fecha;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }
}
