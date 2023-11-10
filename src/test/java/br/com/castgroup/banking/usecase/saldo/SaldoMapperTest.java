package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.Conta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
class SaldoMapperTest {
    @InjectMocks
    private SaldoMapperImpl mapper;
    
    @Test
    void toWebNull() {
        SaldoWeb web = mapper.toWeb(null);
        assertNull(web);
    }
    
    @Test
    void toWebCorrentistaNull() {
        Conta contaOrigem = createConta(1, 1, "1111", null);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        SaldoWeb web = mapper.toWeb(saldoOrigem);
        assertEquals(new BigDecimal("1000"), web.getValor());
        assertNull(web.getConta().getCorrentista());
    }
    
    @Test
    void toWebContaNull() {
        Saldo saldoOrigem = createSaldo(1, null, new BigDecimal("1000"));
        
        SaldoWeb web = mapper.toWeb(saldoOrigem);
        
        assertEquals(new BigDecimal("1000"), web.getValor());
        assertNull(web.getConta());
    }
}
