package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateSaldoZeradoUseCase.class)
class CreateSaldoZeradoUseCaseTest {
    @Autowired
    private CreateSaldoZeradoUseCase service;
    
    @MockBean
    private SaldoRepository saldoRepository;
    
    @Test
    void execute() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        when(saldoRepository.findByConta(toCreate)).thenReturn(Optional.empty());
        Saldo created = service.execute(toCreate);
        assertEquals(ZERO, created.getValor());
    }
    
    @Test
    void executeContaJaExiste() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(4, "4444", correntistaMock);
        Saldo saldo = createSaldo(4, conta, new BigDecimal("1000"));
        when(saldoRepository.findByConta(conta)).thenReturn(Optional.of(saldo));
        assertThrows(NotFoundException.class, () -> service.execute(conta), "Saldo Conta 4 já existe");
    }
}
