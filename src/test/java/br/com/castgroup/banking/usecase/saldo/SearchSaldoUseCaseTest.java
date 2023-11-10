package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchSaldoUseCaseTest {
    @InjectMocks
    SearchSaldoUseCase searchSaldo;
    
    @Mock
    SaldoRepository repository;
    
    @Test
    void execute() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta contaMock = createConta(3, "3333", correntistaMock);
        Saldo saldoMock = createSaldo(3, contaMock, new BigDecimal("3000"));
        when(repository.findAllByConta_NumeroOrderByIdAsc(PageRequest.of(0, 10), 3)).thenReturn(new PageImpl<>(singletonList(saldoMock)));
        Page<Saldo> saldos = searchSaldo.execute(PageRequest.of(0, 10), "3");
        assertEquals(1, saldos.getContent().size());
        assertEquals(new BigDecimal("3000"), saldos.getContent().get(0).getValor());
    }

    @Test
    void executeNull() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta contaMock = createConta(3, "3333", correntistaMock);
        Saldo saldoMock = createSaldo(3, contaMock, new BigDecimal("3000"));
        when(repository.findAllByOrderById(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(singletonList(saldoMock)));
        Page<Saldo> saldos = searchSaldo.execute(PageRequest.of(0, 10), null);
        assertEquals(1, saldos.getContent().size());
        assertEquals(new BigDecimal("3000"), saldos.getContent().get(0).getValor());
    }

    @Test
    void executeEmpty() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta contaMock = createConta(3, "3333", correntistaMock);
        Saldo saldoMock = createSaldo(3, contaMock, new BigDecimal("3000"));
        when(repository.findAllByOrderById(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(singletonList(saldoMock)));
        Page<Saldo> saldos = searchSaldo.execute(PageRequest.of(0, 10), "");
        assertEquals(1, saldos.getContent().size());
        assertEquals(new BigDecimal("3000"), saldos.getContent().get(0).getValor());
    }
}
