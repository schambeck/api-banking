package br.com.castgroup.banking.usecase.saque;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper
public interface SaqueMapper {
    SaqueMapper INSTANCE = Mappers.getMapper(SaqueMapper.class);

    SaqueWeb toWeb(Saque entity);

    Saque toEntity(SaqueWeb web);

    void copy(Saque source, @MappingTarget Saque target);
}
