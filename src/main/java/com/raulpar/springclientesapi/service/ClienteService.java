package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los clientes.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Devuelve todos los clientes registrados en la base de datos.
     */
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id ID del cliente
     * @return Optional que contiene el cliente si se encuentra
     */
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Guarda o actualiza un cliente en la base de datos.
     *
     * @param cliente Objeto cliente a persistir
     * @return Cliente guardado
     */
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

    /**
     * Busca un cliente por su DNI.
     *
     * @param dni DNI del cliente
     * @return Optional con el cliente si se encuentra
     */
    public Optional<Cliente> findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

}
