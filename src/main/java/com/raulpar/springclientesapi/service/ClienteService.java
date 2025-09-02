package com.raulpar.springclientesapi.service;

import com.raulpar.springclientesapi.dto.ClienteInputDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDetailDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDto;
import com.raulpar.springclientesapi.mapper.ClienteMapper;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    /**
     * Devuelve todos los clientes registrados en la base de datos.
     */
    public List<ClienteOutputDto> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toOutputList(clientes);
    }

    /**
     * Actualiza un cliente existente con los datos recibidos en el DTO.
     *
     * @param id  ID del cliente a actualizar
     * @param dto DTO con los datos nuevos para el cliente
     * @return Optional con el DTO detallado del cliente actualizado si se encontró y actualizó,
     * o un Optional vacío si no existe el cliente con ese ID
     */
    public Optional<ClienteOutputDetailDto> update(Long id, ClienteInputDto dto) {
        return clienteRepository.findById(id)
                .map(existing -> {
                    Cliente actualizado = clienteMapper.toEntity(dto);
                    actualizado.setIdCliente(id);
                    Cliente guardado = clienteRepository.save(actualizado);
                    return clienteMapper.toDetailDto(guardado);
                });
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id ID del cliente
     * @return Optional que contiene el cliente si se encuentra
     */
    public Optional<ClienteOutputDetailDto> findById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDetailDto);
    }

    /**
     * Guarda un cliente en la base de datos.
     *
     * @param clientedto Objeto cliente a persistir
     * @return Cliente guardado
     */
    public ClienteOutputDetailDto save(ClienteInputDto clientedto) {
        Cliente cliente = clienteMapper.toEntity(clientedto);
        Cliente saved = clienteRepository.save(cliente);
        return clienteMapper.toDetailDto(saved);
    }

    /**
     * Elimina un cliente si existe.
     *
     * @param id ID del cliente
     * @return Un Optional con el ClienteOutputDetailDto del cliente eliminado,
     * o vacío si no existía un cliente con ese ID
     */
    public Optional<ClienteOutputDetailDto> deleteById(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.deleteById(id);
                    return clienteMapper.toDetailDto(cliente);
                });
    }

    /**
     * Busca un cliente por su DNI.
     *
     * @param dni DNI del cliente
     * @return Optional con el cliente si se encuentra
     */
    public Optional<ClienteOutputDetailDto> findByDni(String dni) {
        return clienteRepository.findByDni(dni)
                .map(clienteMapper::toDetailDto);
    }


}
