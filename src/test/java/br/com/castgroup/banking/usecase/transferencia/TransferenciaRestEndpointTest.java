package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
class TransferenciaRestEndpointTest {
    @InjectMocks
    TransferenciaRestEndpoint endpoint;
    
    @Mock
    TransferenciaController service;
    
    @Test
    void create() {
        CreateTransferenciaRequest request = CreateTransferenciaRequest.builder().build();
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));
        when(service.create(request)).thenReturn(transferencia);
        
        ResponseEntity<TransferenciaWeb> response = endpoint.create(request);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("111"), response.getBody().getValor());
    }
}
