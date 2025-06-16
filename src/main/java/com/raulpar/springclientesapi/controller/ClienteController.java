package com.raulpar.springclientesapi.controller;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteservice;

    public ClienteController(ClienteService clienteservice){
        this.clienteservice = clienteservice;
    }

    @GetMapping
    public List<Cliente> getAll() {
        return clienteservice.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cliente> getById(@PathVariable Long id) {
        return clienteservice.findById(id);
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteservice.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clienteservice.deleteById(id);
    }

    @GetMapping("/dni/{dni}")
    public Optional<Cliente> getByDni(@PathVariable String dni) {
        return clienteservice.findByDni(dni);
    }
}
