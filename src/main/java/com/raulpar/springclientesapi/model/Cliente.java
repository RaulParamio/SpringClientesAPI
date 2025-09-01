package com.raulpar.springclientesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pedidos")
@Schema(description = "Entity that represents a customer.")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    @Schema(description = "Unique identifier of the customer.", example = "1")
    private Long idCliente;

    @NotBlank(message = "DNI obligatorio")
    @Column(name = "dni", unique = true, nullable = false)
    @Schema(description = "National ID (DNI) of the customer.", example = "12345678Z")
    private String dni;

    @Column(name = "nombre")
    @Schema(description = "Customer's first name ", example = "Fernando")
    private String nombre;

    @Column(name = "apellidos")
    @Schema(description = "Customer's last name ", example = "Fernandez")
    private String apellidos;

    @Column(name = "email")
    @Schema(description = "Customer's email address.", example = "Fernando123@gmail.com")
    private String email;

    @Column(name = "calle")
    @Schema(description = "Customer's Address ", example = "Calle Barcelona")
    private String calle;

    @Column(name = "municipio")
    @Schema(description = "Customer's city", example = "Madrid")
    private String municipio;

    @Column(name = "provincia")
    @Schema(description = "Customer's Province.", example = "Madrid")
    private String provincia;

    /**
     * Lista de pedidos asociados a este cliente.
     * Se ignora en la serialización JSON para evitar ciclos infinitos.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    public Cliente(String dni, String nombre, String apellidos, String email,
                   String calle, String municipio, String provincia) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.calle = calle;
        this.municipio = municipio;
        this.provincia = provincia;
    }
}