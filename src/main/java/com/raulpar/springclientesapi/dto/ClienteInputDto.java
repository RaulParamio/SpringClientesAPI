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
@Schema(description = "DTO to save a customer")
public class ClienteInputDto {

    @NotBlank
    @Schema(description = "Customer DNI (National ID number)", example = "12345678Z")
    private String dni;

    @NotBlank
    @Schema(description = "Customer name", example = "Juan")
    private String nombre;

    @NotBlank
    @Schema(description = "Customer last name", example = "Pérez")
    private String apellidos;

    @NotBlank
    @Email
    @Schema(description = "Email Address", example = "juan@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Street Address", example = "Calle Malaga 10")
    private String calle;

    @NotBlank
    @Schema(description = "City", example = "Madrid")
    private String municipio;

    @NotBlank
    @Schema(description = "Province / State", example = "Madrid")
    private String provincia;
}
