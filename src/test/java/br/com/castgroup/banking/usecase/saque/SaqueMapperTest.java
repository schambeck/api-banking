package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.conta.ContaWeb;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.correntista.CorrentistaWeb;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.saldo.SaldoWeb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.conta.ContaWebUtil.createContaWeb;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.saldo.SaldoWebUtil.createSaldoWeb;
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;
import static br.com.castgroup.banking.usecase.saque.SaqueWebUtil.createSaqueWeb;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
class SaqueMapperTest {
    @InjectMocks
    private SaqueMapperImpl mapper;
    
    @Test
    void toWeb() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, LocalDate.now(),  new BigDecimal("111"));
        SaqueWeb web = mapper.toWeb(saque);
        assertEquals(new BigDecimal("111"), web.getValor());
    }
    
    @Test
    void toWebCorrentistaNull() {
        Conta conta = createConta(1, 1, "1111", null);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, LocalDate.now(), new BigDecimal("111"));
        SaqueWeb web = mapper.toWeb(saque);
        assertEquals(new BigDecimal("111"), web.getValor());
        assertNull(web.getSaldo().getConta().getCorrentista());
    }
    
    @Test
    void toWebContaNull() {
        Saldo saldo = createSaldo(1, null, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, LocalDate.now(), new BigDecimal("111"));
        SaqueWeb web = mapper.toWeb(saque);
        assertEquals(new BigDecimal("111"), web.getValor());
        assertNull(web.getSaldo().getConta());
    }
    
    @Test
    void toWebSaldoNull() {
        Saque saque = createSaque(null, LocalDate.now(), new BigDecimal("111"));
        SaqueWeb web = mapper.toWeb(saque);
        assertEquals(new BigDecimal("111"), web.getValor());
        assertNull(web.getSaldo());
    }
    
    @Test
    void toWebNull() {
        SaqueWeb web = mapper.toWeb(null);
        assertNull(web);
    }
    
    @Test
    void toEntity() {
        CorrentistaWeb correntista = createCorrentistaWeb(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        ContaWeb conta = createContaWeb(1, 1, "1111", correntista);
        SaldoWeb saldo = createSaldoWeb(1, conta, new BigDecimal("1000"));
        SaqueWeb web = createSaqueWeb(saldo, new BigDecimal("111"));
        
        Saque entity = mapper.toEntity(web);
        assertEquals(new BigDecimal("111"), entity.getValor());
    }
    
    @Test
    void toEntityCorrentistaNull() {
        ContaWeb conta = createContaWeb(1, 1, "1111", null);
        SaldoWeb saldo = createSaldoWeb(1, conta, new BigDecimal("1000"));
        SaqueWeb saque = createSaqueWeb(saldo, new BigDecimal("111"));
        Saque entity = mapper.toEntity(saque);
        assertEquals(new BigDecimal("111"), entity.getValor());
        assertNull(entity.getSaldo().getConta().getCorrentista());
    }
    
    @Test
    void toEntityContaNull() {
        SaldoWeb saldo = createSaldoWeb(1, null, new BigDecimal("1000"));
        SaqueWeb saque = createSaqueWeb(saldo, new BigDecimal("111"));
        Saque entity = mapper.toEntity(saque);
        assertEquals(new BigDecimal("111"), entity.getValor());
        assertNull(entity.getSaldo().getConta());
    }
    
    @Test
    void toEntitySaldoNull() {
        SaqueWeb saque = createSaqueWeb(null, new BigDecimal("111"));
        Saque entity = mapper.toEntity(saque);
        assertEquals(new BigDecimal("111"), entity.getValor());
        assertNull(entity.getSaldo());
    }
    
    @Test
    void toEntityNull() {
        Saque entity = mapper.toEntity(null);
        assertNull(entity);
    }
    
    @Test
    void copy() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque source = createSaque(saldo, LocalDate.now(), new BigDecimal("111"));
        
        Saque target = createSaque(saldo, LocalDate.now(), new BigDecimal("222"));
        mapper.copy(source, target);
        
        assertEquals(new BigDecimal("111"), target.getValor());
    }
    
    @Test
    void copyNull() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque target = createSaque(saldo, LocalDate.now(), new BigDecimal("111"));
        mapper.copy(null, target);
        
        assertEquals(new BigDecimal("111"), target.getValor());
    }
}
