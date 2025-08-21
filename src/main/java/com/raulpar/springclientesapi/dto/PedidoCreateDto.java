package com.raulpar.springclientesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO for creating an order")
public class PedidoCreateDto {
    @Schema(description = "Customer's Unique identifier", example = "1")
    private Long clienteId;
}
