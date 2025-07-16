package com.raulpar.springclientesapi.mapper;

import com.raulpar.springclientesapi.dto.ClienteInputDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDetailDto;
import com.raulpar.springclientesapi.dto.ClienteOutputDto;
import com.raulpar.springclientesapi.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteInputDto dto);

    ClienteOutputDto toDto(Cliente cliente);

    ClienteOutputDetailDto toDetailDto(Cliente cliente);

    List<ClienteOutputDto> toOutputList(List<Cliente> clientes);
}
