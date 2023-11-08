package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FindContaUseCase.class)
class FindContaUseCaseTest {
    @Autowired
    private FindContaUseCase service;
    
    @MockBean
    private ContaRepository repository;
    
    @Test
    void find() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta createdMock = createConta(3, "3333", correntistaMock);
        when(repository.findById(3)).thenReturn(Optional.of(createdMock));
        Conta conta = service.execute(3);
        assertEquals(3, conta.getNumero());
    }
    
    @Test
    void findNotFound() {
        when(repository.findById(5)).thenReturn(Optional.empty());
        NotFoundException exc = assertThrows(NotFoundException.class, () -> service.execute(5));
        assertEquals("Entity 5 not found", exc.getMessage());
    }
}
