package com.raulpar.springclientesapi.mapper;

import com.raulpar.springclientesapi.dto.PedidoDto;
import com.raulpar.springclientesapi.model.Pedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoDto toDto(Pedido pedido);

    Pedido toEntity(PedidoDto pedidoDto);

    List<PedidoDto> toDtoList(List<Pedido> pedidos);
}

