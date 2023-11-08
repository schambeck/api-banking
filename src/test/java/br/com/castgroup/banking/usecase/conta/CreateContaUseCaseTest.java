package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateContaUseCase.class)
class CreateContaUseCaseTest {
    @Autowired
    private CreateContaUseCase service;
    
    @MockBean
    private ContaRepository repository;
    
    @Test
    void create() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        Conta createdMock = createConta(4, 4, "4444", correntistaMock);
        when(repository.save(toCreate)).thenReturn(createdMock);
        Conta created = service.execute(toCreate);
        assertEquals(4, created.getNumero());
    }
}
