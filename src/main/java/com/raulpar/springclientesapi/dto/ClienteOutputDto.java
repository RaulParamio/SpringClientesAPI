package com.raulpar.springclientesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para mostrar cliente")
public class ClienteOutputDto{

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

}
