package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FindSaldoByContaUseCase.class)
class FindSaldoByContaUseCaseTest {
    @Autowired
    private FindSaldoByContaUseCase service;
    
    @MockBean
    private SaldoRepository repository;
    
    @Test
    void find() {
        Correntista correntista = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta conta = createConta(3, "3333", correntista);
        Saldo createdMock = createSaldo(3, conta, ZERO);
        when(repository.findByConta_Id(3)).thenReturn(Optional.of(createdMock));
        Saldo saldo = service.execute(3);
        assertEquals(ZERO, saldo.getValor());
    }
    
    @Test
    void findNotFound() {
        when(repository.findById(5)).thenReturn(Optional.empty());
        NotFoundException exc = assertThrows(NotFoundException.class, () -> service.execute(5));
        assertEquals("Saldo da Conta 5 não encontrado", exc.getMessage());
    }
}
