package br.com.castgroup.banking.usecase.transferencia;

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

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.conta.ContaWebUtil.createContaWeb;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.saldo.SaldoWebUtil.createSaldoWeb;
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaWebUtil.createTransferenciaWeb;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
class TransferenciaMapperTest {
    @InjectMocks
    private TransferenciaMapperImpl mapper;
    
    @Test
    void toWeb() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntista);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));

        TransferenciaWeb web = mapper.toWeb(transferencia);
        
        assertEquals(new BigDecimal("111"), web.getValor());
    }
    
    @Test
    void toWebCorrentistaNull() {
        Conta contaOrigem = createConta(1, 1, "1111", null);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));
        TransferenciaWeb web = mapper.toWeb(transferencia);
        assertEquals(new BigDecimal("111"), web.getValor());
        assertNull(web.getSaldoOrigem().getConta().getCorrentista());
    }
    
    @Test
    void toWebContaNull() {
        Saldo saldoOrigem = createSaldo(1, null, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));
        
        TransferenciaWeb web = mapper.toWeb(transferencia);
        
        assertEquals(new BigDecimal("111"), web.getValor());
        assertNull(web.getSaldoOrigem().getConta());
    }
    
    @Test
    void toWebSaldoNull() {
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia transferencia = createTransferencia(null, saldoDestino, new BigDecimal("111"));
        
        TransferenciaWeb web = mapper.toWeb(transferencia);
        
        assertEquals(new BigDecimal("111"), web.getValor());
        assertNull(web.getSaldoOrigem());
    }
    
    @Test
    void toWebNull() {
        TransferenciaWeb web = mapper.toWeb(null);
        assertNull(web);
    }
    
    @Test
    void toEntity() {
        CorrentistaWeb correntistaOrigem = createCorrentistaWeb(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        ContaWeb contaOrigem = createContaWeb(1, 1, "1111", correntistaOrigem);
        SaldoWeb saldoOrigem = createSaldoWeb(1, contaOrigem, new BigDecimal("1000"));
        CorrentistaWeb correntistaDestino = createCorrentistaWeb(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        ContaWeb contaDestino = createContaWeb(3, 3, "3333", correntistaDestino);
        SaldoWeb saldoDestino = createSaldoWeb(3, contaDestino, new BigDecimal("3000"));
        TransferenciaWeb web = createTransferenciaWeb(saldoOrigem, saldoDestino, new BigDecimal("111"));
        
        Transferencia entity = mapper.toEntity(web);
        
        assertEquals(new BigDecimal("111"), entity.getValor());
    }
    
    @Test
    void toEntityCorrentistaNull() {
        ContaWeb contaOrigem = createContaWeb(1, 1, "1111", null);
        SaldoWeb saldoOrigem = createSaldoWeb(1, contaOrigem, new BigDecimal("1000"));
        CorrentistaWeb correntistaDestino = createCorrentistaWeb(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        ContaWeb contaDestino = createContaWeb(3, 3, "3333", correntistaDestino);
        SaldoWeb saldoDestino = createSaldoWeb(3, contaDestino, new BigDecimal("3000"));
        TransferenciaWeb web = createTransferenciaWeb(saldoOrigem, saldoDestino, new BigDecimal("111"));

        Transferencia entity = mapper.toEntity(web);

        assertEquals(new BigDecimal("111"), entity.getValor());
        assertNull(entity.getSaldoOrigem().getConta().getCorrentista());
    }
    
    @Test
    void toEntityContaNull() {
        SaldoWeb saldoOrigem = createSaldoWeb(1, null, new BigDecimal("1000"));
        CorrentistaWeb correntistaDestino = createCorrentistaWeb(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        ContaWeb contaDestino = createContaWeb(3, 3, "3333", correntistaDestino);
        SaldoWeb saldoDestino = createSaldoWeb(3, contaDestino, new BigDecimal("3000"));
        TransferenciaWeb web = createTransferenciaWeb(saldoOrigem, saldoDestino, new BigDecimal("111"));

        Transferencia entity = mapper.toEntity(web);

        assertEquals(new BigDecimal("111"), entity.getValor());
        assertNull(entity.getSaldoOrigem().getConta());
    }
    
    @Test
    void toEntitySaldoNull() {
        CorrentistaWeb correntistaDestino = createCorrentistaWeb(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        ContaWeb contaDestino = createContaWeb(3, 3, "3333", correntistaDestino);
        SaldoWeb saldoDestino = createSaldoWeb(3, contaDestino, new BigDecimal("3000"));
        TransferenciaWeb web = createTransferenciaWeb(null, saldoDestino, new BigDecimal("111"));

        Transferencia entity = mapper.toEntity(web);

        assertEquals(new BigDecimal("111"), entity.getValor());
        assertNull(entity.getSaldoOrigem());
    }
    
    @Test
    void toEntityNull() {
        Transferencia entity = mapper.toEntity(null);
        assertNull(entity);
    }
    
    @Test
    void copy() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntista);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia source = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));
        Transferencia target = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("222"));

        mapper.copy(source, target);
        
        assertEquals(new BigDecimal("222"), target.getValor());
    }
    
    @Test
    void copyNull() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntista);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia target = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));
        mapper.copy(null, target);
        
        assertEquals(new BigDecimal("111"), target.getValor());
    }
}
