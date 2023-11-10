package br.com.castgroup.banking.usecase.saldo;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaldoMapper {
    SaldoMapper INSTANCE = Mappers.getMapper(SaldoMapper.class);

    SaldoWeb toWeb(Saldo entity);
}
