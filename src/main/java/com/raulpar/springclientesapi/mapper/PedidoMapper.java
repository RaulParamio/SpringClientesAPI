package com.raulpar.springclientesapi.mapper;

import com.raulpar.springclientesapi.dto.PedidoCreateDto;
import com.raulpar.springclientesapi.dto.PedidoDto;
import com.raulpar.springclientesapi.model.Cliente;
import com.raulpar.springclientesapi.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "cliente.idCliente", target = "idCliente")
    PedidoDto toDto(Pedido pedido);

    @Mapping(source = "idCliente", target = "cliente")
    Pedido toEntity(PedidoDto pedidoDto);

    @Mapping(source = "idCliente", target = "cliente")
    Pedido toEntity(PedidoCreateDto pedidoCreateDto);

    @Mapping(source = "cliente.idCliente", target = "idCliente")
    PedidoCreateDto toCreateDto(Pedido pedido);

    List<PedidoDto> toDtoList(List<Pedido> pedidos);

    default Cliente map(Long id) {
        if (id == null) return null;
        Cliente c = new Cliente();
        c.setIdCliente(id);
        return c;
    }

    default Long map(Cliente cliente) {
        return (cliente == null) ? null : cliente.getIdCliente();
    }

}

