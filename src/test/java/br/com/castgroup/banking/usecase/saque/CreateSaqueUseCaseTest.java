package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.exception.BusinessException;
import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.movimentacao.Movimentacao;
import br.com.castgroup.banking.usecase.movimentacao.MovimentacaoRepository;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.saldo.SaldoRepository;
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
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateSaqueUseCaseTest {
    @InjectMocks
    private CreateSaqueUseCase service;
    
    @Mock
    private SaqueRepository saqueRepository;
    
    @Mock
    private SaldoRepository saldoRepository;
    
    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    
    @Test
    void execute() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saldo updatedSaldo = createSaldo(1, conta, new BigDecimal("1111"));
        Saque toCreate = createSaque(saldo, LocalDate.parse("2023-11-01"), new BigDecimal("111"));
        Saque createdMock = createSaque(1, saldo, LocalDate.parse("2023-11-01"), new BigDecimal("111"));
        Movimentacao createdMovimentacao = createMovimentacao(1, toCreate.getSaldo().getConta(), toCreate.getData(), DEBITO, toCreate.getValor(), updatedSaldo.getValor());
        when(saldoRepository.lockById(conta.getId())).thenReturn(Optional.of(saldo));
        when(saldoRepository.save(saldo)).thenReturn(updatedSaldo);
        when(saqueRepository.save(any(Saque.class))).thenReturn(createdMock);
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(createdMovimentacao);
        Saque created = service.execute(toCreate.getSaldo().getConta(), LocalDate.parse("2023-11-01"), new BigDecimal("111"));
        assertEquals(new BigDecimal("111"), created.getValor());
    }
    
    @Test
    void executeSaldoNotFound() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        when(saldoRepository.lockById(conta.getId())).thenReturn(Optional.empty());
        BigDecimal valor = new BigDecimal("1234");
        assertThrows(NotFoundException.class, () -> service.execute(conta, LocalDate.parse("2023-11-01"), valor), "Saldo da Conta 1 não encontrado");
    }
    
    @Test
    void executeLimiteIndisponivel() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        when(saldoRepository.lockById(conta.getId())).thenReturn(Optional.of(saldo));
        BigDecimal valor = new BigDecimal("1234");
        assertThrows(BusinessException.class, () -> service.execute(conta, LocalDate.parse("2023-11-01"), valor), "Limite da Conta Número %d Agência %s insuficiente");
    }
}
