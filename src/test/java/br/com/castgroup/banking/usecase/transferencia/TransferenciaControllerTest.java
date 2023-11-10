package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.Saldo;
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
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransferenciaControllerTest {
    @InjectMocks
    TransferenciaController controller;
    
    @Mock
    CreateTransferenciaInteractor createTransferencia;
    
    @Test
    void create() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        DadosConta dadosContaOrigem = DadosConta.builder().numero(contaOrigem.getNumero()).agencia(contaOrigem.getAgencia()).build();
        DadosConta dadosContaDestino = DadosConta.builder().numero(contaDestino.getNumero()).agencia(contaDestino.getAgencia()).build();
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, LocalDate.parse("2023-11-01"), new BigDecimal("111"));
        when(createTransferencia.execute(dadosContaOrigem, dadosContaDestino, transferencia.getData(), new BigDecimal("111"))).thenReturn(transferencia);
        
        CreateTransferenciaRequest request = CreateTransferenciaRequest.builder()
                .dadosContaOrigem(dadosContaOrigem)
                .dadosContaDestino(dadosContaDestino)
                .data(LocalDate.parse("2023-11-01"))
                .valor(new BigDecimal("111"))
                .build();
        Transferencia created = controller.create(request);
        
        assertEquals(new BigDecimal("111"), created.getValor());
    }
}
