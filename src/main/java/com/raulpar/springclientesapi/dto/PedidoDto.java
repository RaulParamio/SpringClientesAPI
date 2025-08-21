package com.raulpar.springclientesapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Order's DTO")
public class PedidoDto {

    @Schema(description = "Order's Unique identifier", example = "1")
    private Long numPedido;

    @Schema(description = "Customer's Unique identifier", example = "1")
    private Long clienteId;

    @Schema(description = "Order's Date.", example = "2025-04-19")
    private LocalDateTime fecha;

}
