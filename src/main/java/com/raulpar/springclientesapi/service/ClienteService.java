package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los clientes.
 */
@AllArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;


    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente si existe.
     *
     * @param id ID del cliente
     * @return true si se eliminó, false si no existía
     */
    public boolean deleteById(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public Optional<Cliente> findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
}
