package br.com.castgroup.banking.usecase.correntista;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper
public interface CorrentistaMapper {
    CorrentistaMapper INSTANCE = Mappers.getMapper(CorrentistaMapper.class);

    CorrentistaWeb toWeb(Correntista entity);

    Correntista toEntity(CorrentistaWeb web);

    void copy(Correntista source, @MappingTarget Correntista target);
}
