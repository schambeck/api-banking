package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.CreateSaldoZeradoUseCase;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateContaSaldoZeradoInteractor.class)
class CreateContaSaldoZeradoInteractorTest {
    @Autowired
    private CreateContaSaldoZeradoInteractor service;
    
    @MockBean
    private CreateContaUseCase createConta;
    
    @MockBean
    private CreateSaldoZeradoUseCase createSaldoZerado;
    
    @Test
    void create() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        Conta createdMock = createConta(4, 4, "4444", correntistaMock);
        Saldo saldoMock = createSaldo(createdMock);
        when(createConta.execute(toCreate)).thenReturn(createdMock);
        when(createSaldoZerado.execute(createdMock)).thenReturn(saldoMock);
        Conta created = service.execute(toCreate);
        assertEquals(4, created.getNumero());
    }
}
