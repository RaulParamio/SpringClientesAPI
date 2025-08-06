package com.raulpar.springclientesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para actualizar un cliente")
public class ClienteInputDto {

    @NotBlank
    @Schema(description = "DNI del cliente", example = "12345678Z")
    private String dni;

    @NotBlank
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @NotBlank
    @Schema(description = "Apellidos del cliente", example = "Pérez")
    private String apellidos;

    @NotBlank
    @Email
    @Schema(description = "Correo electrónico", example = "juan@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Calle", example = "Calle Malaga 10")
    private String calle;

    @NotBlank
    @Schema(description = "Municipio", example = "Madrid")
    private String municipio;

    @NotBlank
    @Schema(description = "Provincia", example = "Madrid")
    private String provincia;
}
