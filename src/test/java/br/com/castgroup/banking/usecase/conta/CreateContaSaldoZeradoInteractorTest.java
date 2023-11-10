package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.CreateSaldoZeradoUseCase;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateContaSaldoZeradoInteractorTest {
    @InjectMocks
    private CreateContaSaldoZeradoInteractor service;
    
    @Mock
    private CreateContaUseCase createConta;
    
    @Mock
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
