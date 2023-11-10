package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.conta.ContaRepository;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.saldo.Saldo;
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
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateTransferenciaInteractorTest {
    @InjectMocks
    private CreateTransferenciaInteractor service;
    
    @Mock
    private CreateTransferenciaUseCase createTransferencia;
    
    @Mock
    private ContaRepository contaRepository;
    
    @Test
    void execute() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, LocalDate.parse("2023-11-01"), new BigDecimal("111"));
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByNumeroAndAgencia(3, "3333")).thenReturn(Optional.of(contaDestino));
        when(createTransferencia.execute(contaOrigem, contaDestino, transferencia.getData(), new BigDecimal("111"))).thenReturn(transferencia);
        
        DadosConta dadosContaOrigem = DadosConta.builder().numero(contaOrigem.getNumero()).agencia(contaOrigem.getAgencia()).build();
        DadosConta dadosContaDestino = DadosConta.builder().numero(contaDestino.getNumero()).agencia(contaDestino.getAgencia()).build();
        Transferencia created = service.execute(dadosContaOrigem, dadosContaDestino, transferencia.getData(), new BigDecimal("111"));
        assertEquals(new BigDecimal("111"), created.getValor());
    }
    
    @Test
    void executeContaOrigemNotFound() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.empty());
        when(contaRepository.findByNumeroAndAgencia(3, "3333")).thenReturn(Optional.of(contaDestino));
        
        DadosConta dadosContaOrigem = DadosConta.builder().numero(contaOrigem.getNumero()).agencia(contaOrigem.getAgencia()).build();
        DadosConta dadosContaDestino = DadosConta.builder().numero(contaDestino.getNumero()).agencia(contaDestino.getAgencia()).build();
        BigDecimal valor = new BigDecimal("111");
        LocalDate data = LocalDate.parse("2023-11-01");
        assertThrows(NotFoundException.class, () -> service.execute(dadosContaOrigem, dadosContaDestino, data, valor), "Conta 1 e agência 1111 não encontrada");
    }
    
    @Test
    void executeContaDestinoNotFound() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        when(contaRepository.findByNumeroAndAgencia(1, "1111")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByNumeroAndAgencia(3, "3333")).thenReturn(Optional.empty());
        
        DadosConta dadosContaOrigem = DadosConta.builder().numero(contaOrigem.getNumero()).agencia(contaOrigem.getAgencia()).build();
        DadosConta dadosContaDestino = DadosConta.builder().numero(contaDestino.getNumero()).agencia(contaDestino.getAgencia()).build();
        BigDecimal valor = new BigDecimal("111");
        LocalDate data = LocalDate.parse("2023-11-01");
        assertThrows(NotFoundException.class, () -> service.execute(dadosContaOrigem, dadosContaDestino, data, valor), "Conta 3 e agência 3333 não encontrada");
    }
}
