package com.raulpar.springclientesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para mostrar cliente detallado")
public class ClienteOutputDetailDto {

    @Schema(description = "ID del cliente", example = "1")
    private Long idCliente;

    @Schema(description = "DNI del cliente", example = "12345678Z")
    private String dni;

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @Schema(description = "Apellidos del cliente", example = "Pérez")
    private String apellidos;

    @Email
    @Schema(description = "Correo electrónico", example = "juan@gmail.com")
    private String email;

    @Schema(description = "Calle", example = "Calle Malaga 10")
    private String calle;

    @Schema(description = "Municipio", example = "Madrid")
    private String municipio;

    @Schema(description = "Provincia", example = "Madrid")
    private String provincia;
}

