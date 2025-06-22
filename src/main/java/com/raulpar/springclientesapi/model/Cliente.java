package com.raulpar.springclientesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pedidos")
@Schema(description = "Entidad que representa a un cliente / Entity that represents a customer.")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    @Schema(description = "Identificador único del cliente / Unique identifier of the customer.", example = "1")
    private Long idCliente;

    @Column(name = "dni", unique = true, nullable = false)
    @Schema(description = "DNI del cliente / National ID (DNI) of the customer.", example = "12345678Z")
    private String dni;

    @Column(name = "nombre")
    @Schema(description = "Nombre del cliente / Customer's first name .", example = "Fernando")
    private String nombre;

    @Column(name = "apellidos")
    @Schema(description = "Apellido del cliente / Customer's last name ", example = "Fernandez")
    private String apellidos;

    @Column(name = "email")
    @Schema(description = "Email del cliente / Customer's email address.", example = "Fernando123@gmail.com")
    private String email;

    @Column(name = "calle")
    @Schema(description = "Calle del cliente / Customer's Address ", example = "Calle Barcelona")
    private String calle;

    @Column(name = "municipio")
    @Schema(description = "Municipio del cliente / Customer's city", example = "Madrid")
    private String municipio;

    @Column(name = "provincia")
    @Schema(description = "Provincia del cliente / Customer's Province.", example = "Madrid")
    private String provincia;


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