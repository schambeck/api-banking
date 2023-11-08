package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.conta.ContaRepository;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.transferencia.DadosConta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateSaqueInteractor.class)
class CreateSaqueInteractorTest {
    @Autowired
    private CreateSaqueInteractor service;
    
    @MockBean
    private CreateSaqueUseCase createSaque;
    
    @MockBean
    private ContaRepository contaRepository;
    
    @Test
    void execute() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, new BigDecimal("111"));
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.of(conta));
        when(createSaque.execute(conta, new BigDecimal("111"))).thenReturn(saque);
        
        DadosConta dadosConta = DadosConta.builder().numero(conta.getNumero()).agencia(conta.getAgencia()).build();
        Saque created = service.execute(dadosConta, new BigDecimal("111"));
        assertEquals(new BigDecimal("111"), created.getValor());
    }
    
    @Test
    void executeContaNotFound() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.empty());
        
        DadosConta dadosConta = DadosConta.builder().numero(conta.getNumero()).agencia(conta.getAgencia()).build();
        BigDecimal valor = new BigDecimal("111");
        assertThrows(NotFoundException.class, () -> service.execute(dadosConta, valor), "Conta 1 e agência 1111 não encontrada");
    }
}
