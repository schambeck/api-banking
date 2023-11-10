package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.conta.ContaRepository;
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
import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateSaqueInteractorTest {
    @InjectMocks
    private CreateSaqueInteractor service;
    
    @Mock
    private CreateSaqueUseCase createSaque;
    
    @Mock
    private ContaRepository contaRepository;
    
    @Test
    void execute() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saque saque = createSaque(saldo, LocalDate.parse("2023-11-01"), new BigDecimal("111"));
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.of(conta));
        when(createSaque.execute(conta, LocalDate.parse("2023-11-01"), new BigDecimal("111"))).thenReturn(saque);
        
        DadosConta dadosConta = DadosConta.builder().numero(conta.getNumero()).agencia(conta.getAgencia()).build();
        Saque created = service.execute(dadosConta, saque.getData(), new BigDecimal("111"));
        assertEquals(new BigDecimal("111"), created.getValor());
    }
    
    @Test
    void executeContaNotFound() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.empty());
        
        DadosConta dadosConta = DadosConta.builder().numero(conta.getNumero()).agencia(conta.getAgencia()).build();
        BigDecimal valor = new BigDecimal("111");
        assertThrows(NotFoundException.class, () -> service.execute(dadosConta, LocalDate.parse("2023-11-01"), valor), "Conta 1 e agência 1111 não encontrada");
    }
}
