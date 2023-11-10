package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.correntista.CorrentistaRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {
    @Autowired
    protected CorrentistaRepository correntistaRepository;

    public abstract ContaWeb toWeb(Conta entity);
    
    public abstract Conta toEntity(ContaWeb web);
    
    public abstract void copy(Conta source, @MappingTarget Conta target);
    
    @AfterMapping
    public void loadRelations(@MappingTarget Conta.ContaBuilder target, ContaWeb web) {
        if (web.getCorrentista() != null) {
            target.correntista(correntistaRepository.findById(web.getCorrentista().getId()).orElseThrow(() -> new NotFoundException("Entity %d not found".formatted(web.getCorrentista().getId()))));
        }
    }
}
