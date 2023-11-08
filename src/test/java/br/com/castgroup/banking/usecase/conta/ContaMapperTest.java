package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.CorrentistaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = ContaMapperImpl.class)
class ContaMapperTest {
    @Autowired
    private ContaMapperImpl mapper;
    
    @MockBean
    private CorrentistaRepository correntistaRepository;
    
    @Test
    void toWebNull() {
        ContaWeb web = mapper.toWeb(null);
        assertNull(web);
    }
    @Test
    void toEntityNull() {
        Conta entity = mapper.toEntity(null);
        assertNull(entity);
    }
}
