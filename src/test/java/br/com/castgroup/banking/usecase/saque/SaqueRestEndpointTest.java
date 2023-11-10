package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.transferencia.DadosConta;
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
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
class SaqueRestEndpointTest {
    @InjectMocks
    SaqueRestEndpoint endpoint;
    
    @Mock
    SaqueController service;
    
    @Test
    void create() {
        DadosConta dadosConta = DadosConta.builder()
                .numero(1)
                .agencia("1111")
                .build();
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, new BigDecimal("111"));
        CreateSaqueRequest request = CreateSaqueRequest.builder()
                .dadosConta(dadosConta)
                .valor(new BigDecimal("111"))
                .build();
        when(service.create(request)).thenReturn(saque);
        
        ResponseEntity<SaqueWeb> response = endpoint.create(request);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("111"), response.getBody().getValor());
    }
}
