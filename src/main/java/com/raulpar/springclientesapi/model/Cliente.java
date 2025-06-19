package com.raulpar.springclientesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pedidos")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private Long idCliente;

    @Column(name = "dni", unique = true, nullable = false)
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "email")
    private String email;

    @Column(name = "calle")
    private String calle;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "provincia")
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