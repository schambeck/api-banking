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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
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

@SpringBootTest(classes = CreateSaqueUseCase.class)
class CreateSaqueUseCaseTest {
    @Autowired
    private CreateSaqueUseCase service;
    
    @MockBean
    private SaqueRepository saqueRepository;
    
    @MockBean
    private SaldoRepository saldoRepository;
    
    @MockBean
    private MovimentacaoRepository movimentacaoRepository;
    
    @Test
    void execute() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        Saldo updatedSaldo = createSaldo(1, conta, new BigDecimal("1111"));
        Saque toCreate = createSaque(saldo, new BigDecimal("111"));
        Saque createdMock = createSaque(1, saldo, new BigDecimal("111"));
        Movimentacao createdMovimentacao = createMovimentacao(1, toCreate.getSaldo().getConta(), toCreate.getData(), DEBITO, toCreate.getValor(), updatedSaldo.getValor());
        when(saldoRepository.findByConta(conta)).thenReturn(Optional.of(saldo));
        when(saldoRepository.save(saldo)).thenReturn(updatedSaldo);
        when(saqueRepository.save(any(Saque.class))).thenReturn(createdMock); // TODO toCreate
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(createdMovimentacao); // TODO movimentacao
        Saque created = service.execute(toCreate.getSaldo().getConta(), new BigDecimal("111"));
        assertEquals(new BigDecimal("111"), created.getValor());
    }
    
    @Test
    void executeSaldoNotFound() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        when(saldoRepository.findByConta(conta)).thenReturn(Optional.empty());
        BigDecimal valor = new BigDecimal("1234");
        assertThrows(NotFoundException.class, () -> service.execute(conta, valor), "Saldo da Conta 1 não encontrado");
    }
    
    @Test
    void executeLimiteIndisponivel() {
        Correntista correntista = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta conta = createConta(1, 1, "1111", correntista);
        Saldo saldo = createSaldo(1, conta, new BigDecimal("1000"));
        when(saldoRepository.findByConta(conta)).thenReturn(Optional.of(saldo));
        BigDecimal valor = new BigDecimal("1234");
        assertThrows(BusinessException.class, () -> service.execute(conta, valor), "Limite da Conta Número %d Agência %s insuficiente");
    }
}
