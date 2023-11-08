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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UpdateContaUseCase.class)
class UpdateContaUseCaseTest {
    @Autowired
    private UpdateContaUseCase service;
    
    @MockBean
    private ContaRepository repository;
    
    @MockBean
    private ContaMapper mapper;
    
    @Test
    void update() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta createdMock = createConta( 1, 1, "1112", correntistaMock);
        when(repository.findById(1)).thenReturn(Optional.of(createdMock));
        doNothing().when(mapper).copy(createdMock, createdMock);
        when(repository.save(createdMock)).thenReturn(createdMock);
        Conta conta = service.execute(1, createdMock);
        assertEquals("1112", conta.getAgencia());
    }
    
    @Test
    void updateNotFound() {
        Correntista correntistaMock = createCorrentista(5, "Marion Vineyard", "marionvineyard@gmail.com", "66120072063");
        Conta createdMock = createConta(5, "5555", correntistaMock);
        when(repository.findById(5)).thenReturn(Optional.empty());
        when(repository.save(createdMock)).thenReturn(createdMock);
        NotFoundException exc = assertThrows(NotFoundException.class, () -> service.execute(5, createdMock));
        assertEquals("Entity 5 not found", exc.getMessage());
    }
}
