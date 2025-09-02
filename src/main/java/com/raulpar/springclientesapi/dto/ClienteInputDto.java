package com.raulpar.springclientesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Pattern(
            regexp = "^\\d{8}[A-Z]$",
            message = "The DNI must contain 8 digits followed by an uppercase letter"
    )
    private String dni;

    @NotBlank
    @Size(min = 2, max = 50, message = "The first name must have between 2 and 50 characters")
    @Schema(description = "Customer first name", example = "Juan")
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 50, message = "The last name must have between 2 and 50 characters")
    @Schema(description = "Customer last name", example = "Pérez")
    private String apellidos;

    @NotBlank
    @Email
    @Size(max = 100, message = "The email must not exceed 100 characters")
    @Schema(description = "Email Address", example = "juan@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 2, max = 100, message = "The street address must have between 2 and 100 characters")
    @Schema(description = "Street Address", example = "Calle Malaga 10")
    private String calle;

    @NotBlank
    @Size(min = 2, max = 50, message = "The city must have between 2 and 50 characters")
    @Schema(description = "City", example = "Madrid")
    private String municipio;

    @NotBlank
    @Size(min = 2, max = 50, message = "The province/state must have between 2 and 50 characters")
    @Schema(description = "Province / State", example = "Madrid")
    private String provincia;
}
