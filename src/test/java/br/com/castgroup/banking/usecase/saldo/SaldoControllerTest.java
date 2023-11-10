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
import java.util.List;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SaldoControllerTest {
    @InjectMocks
    SaldoController controller;
    
    @Mock
    FindSaldoUseCase findSaldo;
    
    @Mock
    SearchSaldoUseCase searchSaldo;
    
    @Test
    void findById() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        when(findSaldo.execute(1)).thenReturn(saldo);
        
        Saldo found = controller.findById(1);
        assertEquals(new BigDecimal("1000"), found.getValor());
    }
    
    @Test
    void search() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        when(controller.search(PageRequest.of(0, 10), "1")).thenReturn(new PageImpl<>(List.of(saldo)));
        
        Page<Saldo> created = searchSaldo.execute(PageRequest.of(0, 10), "1");
        assertEquals(new BigDecimal("1000"), created.getContent().get(0).getValor());
    }
}
