package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.correntista.CorrentistaRepository;
import br.com.castgroup.banking.usecase.correntista.CorrentistaWeb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.conta.ContaWebUtil.createContaWeb;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ContaMapperTest {
    @InjectMocks
    private ContaMapperImpl mapper;
    
    @Mock
    private CorrentistaRepository correntistaRepository;
    
    @Test
    void toWeb() {
        Correntista correntista = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta conta = createConta(3, "3333", correntista);
        ContaWeb web = mapper.toWeb(conta);
        assertEquals(3, web.getNumero());
    }

    @Test
    void toWebCorrentistaNull() {
        Conta conta = createConta(3, "3333", null);
        ContaWeb web = mapper.toWeb(conta);
        assertEquals(3, web.getNumero());
        assertNull(web.getCorrentista());
    }

    @Test
    void toWebNull() {
        ContaWeb web = mapper.toWeb(null);
        assertNull(web);
    }

    @Test
    void toEntity() {
        CorrentistaWeb correntistaWeb = createCorrentistaWeb(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Correntista correntista = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        ContaWeb web = createContaWeb(3, "3333", correntistaWeb);
        when(correntistaRepository.findById(3)).thenReturn(Optional.of(correntista));
        
        Conta entity = mapper.toEntity(web);
        assertEquals(3, entity.getNumero());
    }
    
    @Test
    void toEntityCorrentistaNotFound() {
        CorrentistaWeb correntistaWeb = createCorrentistaWeb(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        ContaWeb web = createContaWeb(3, "3333", correntistaWeb);
        when(correntistaRepository.findById(3)).thenReturn(Optional.empty());
        
        assertThrows(NotFoundException.class, () -> mapper.toEntity(web), "Entity 3 not found");
    }
    
    @Test
    void toEntityCorrentistaNull() {
        ContaWeb web = createContaWeb(3, "3333", null);
        Conta conta = mapper.toEntity(web);
        assertEquals(3, conta.getNumero());
        assertNull(conta.getCorrentista());
    }
    
    @Test
    void toEntityNull() {
        Conta entity = mapper.toEntity(null);
        assertNull(entity);
    }
    
    @Test
    void copy() {
        Correntista correntista = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta source = createConta(3, "3331", correntista);
        Conta target = createConta(3, "3333", correntista);
        mapper.copy(source, target);
        
        assertEquals("3331", target.getAgencia());
    }
    
    @Test
    void copyNull() {
        Correntista correntista = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta target = createConta(3, "3333", correntista);
        mapper.copy(null, target);
        
        assertEquals("3333", target.getAgencia());
    }
}
