package br.com.castgroup.banking.usecase.transferencia;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper
public interface TransferenciaMapper {
    TransferenciaMapper INSTANCE = Mappers.getMapper(TransferenciaMapper.class);

    TransferenciaWeb toWeb(Transferencia entity);

    Transferencia toEntity(TransferenciaWeb web);

    void copy(Transferencia source, @MappingTarget Transferencia target);
}
