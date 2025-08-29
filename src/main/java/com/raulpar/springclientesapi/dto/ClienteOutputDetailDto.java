package com.raulpar.springclientesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO to display detailed customer information")
public class ClienteOutputDetailDto {

    @Schema(description = "Customer Unique identifier", example = "1")
    private Long idCliente;

    @Schema(description = "Customer DNI (National ID number)", example = "12345678Z")
    private String dni;

    @Schema(description = "Customer first name", example = "Juan")
    private String nombre;

    @Schema(description = "Customer last name", example = "Pérez")
    private String apellidos;

    @Email
    @Schema(description = "Email address", example = "juan@gmail.com")
    private String email;

    @Schema(description = "Street address", example = "Calle Malaga 10")
    private String calle;

    @Schema(description = "City", example = "Madrid")
    private String municipio;

    @Schema(description = "Province / State", example = "Madrid")
    private String provincia;
}

