package br.com.castgroup.banking.usecase.movimentacao;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovimentacaoMapper {
    MovimentacaoMapper INSTANCE = Mappers.getMapper(MovimentacaoMapper.class);

    MovimentacaoWeb toWeb(Movimentacao entity);
}
