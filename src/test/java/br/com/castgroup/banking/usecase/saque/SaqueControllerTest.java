package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.transferencia.DadosConta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SaqueControllerTest {
    @InjectMocks
    SaqueController controller;
    
    @Mock
    CreateSaqueInteractor createSaque;
    
    @Test
    void create() {
        DadosConta dadosConta = DadosConta.builder()
                .numero(1)
                .agencia("1111")
                .build();
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, LocalDate.now(), new BigDecimal("111"));
        when(createSaque.execute(dadosConta, saque.getData(), new BigDecimal("111"))).thenReturn(saque);
        
        CreateSaqueRequest request = CreateSaqueRequest.builder()
                .dadosConta(dadosConta)
                .valor(new BigDecimal("111"))
                .build();
        Saque created = controller.create(request);
        
        assertEquals(new BigDecimal("111"), created.getValor());
    }
}
