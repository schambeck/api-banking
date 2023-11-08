package br.com.castgroup.banking.usecase.transferencia;

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
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.CREDITO;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateTransferenciaUseCase.class)
class CreateTransferenciaUseCaseTest {
    @Autowired
    private CreateTransferenciaUseCase service;
    
    @MockBean
    private TransferenciaRepository transferenciaRepository;
    
    @MockBean
    private SaldoRepository saldoRepository;
    
    @MockBean
    private MovimentacaoRepository movimentacaoRepository;
    
    @Test
    void execute() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Saldo updatedSaldoOrigem = createSaldo(1, saldoOrigem.getConta(), new BigDecimal("889"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        Saldo updatedSaldoDestino = createSaldo(3, saldoDestino.getConta(), new BigDecimal("3111"));
        Transferencia toCreate = createTransferencia(saldoOrigem, saldoDestino, new BigDecimal("111"));
        Transferencia createdMock = createTransferencia(1, saldoOrigem, saldoDestino, new BigDecimal("111"));
        Movimentacao movimentacaoDebitoToCreate = createMovimentacao(toCreate.getSaldoOrigem().getConta(), toCreate.getData(), DEBITO, toCreate.getValor(), updatedSaldoOrigem.getValor());
        Movimentacao movimentacaoCreditoToCreate = createMovimentacao(toCreate.getSaldoDestino().getConta(), toCreate.getData(), CREDITO, toCreate.getValor(), updatedSaldoDestino.getValor());
        Movimentacao movimentacaoDebitoCreated = createMovimentacao(1, toCreate.getSaldoOrigem().getConta(), toCreate.getData(), DEBITO, toCreate.getValor(), updatedSaldoOrigem.getValor());
        Movimentacao movimentacaoCreditoCreated = createMovimentacao(2, toCreate.getSaldoDestino().getConta(), toCreate.getData(), CREDITO, toCreate.getValor(), updatedSaldoDestino.getValor());
        when(saldoRepository.findByConta(contaOrigem)).thenReturn(Optional.of(saldoOrigem));
        when(saldoRepository.findByConta(contaDestino)).thenReturn(Optional.of(saldoDestino));
        when(saldoRepository.save(saldoOrigem)).thenReturn(updatedSaldoOrigem);
        when(saldoRepository.save(saldoDestino)).thenReturn(updatedSaldoDestino);
        when(transferenciaRepository.save(toCreate)).thenReturn(createdMock);
        when(movimentacaoRepository.save(movimentacaoDebitoToCreate)).thenReturn(movimentacaoDebitoCreated); // TODO movimentacao
        when(movimentacaoRepository.save(movimentacaoCreditoToCreate)).thenReturn(movimentacaoCreditoCreated); // TODO movimentacao
        Transferencia created = service.execute(toCreate.getSaldoOrigem().getConta(), toCreate.getSaldoDestino().getConta(), new BigDecimal("111"));
        assertEquals(new BigDecimal("111"), created.getValor());
    }

    @Test
    void executeSaldoNotFound() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        when(saldoRepository.findByConta(contaOrigem)).thenReturn(Optional.empty());
        when(saldoRepository.findByConta(contaDestino)).thenReturn(Optional.of(saldoDestino));
        BigDecimal valor = new BigDecimal("1234");
        assertThrows(NotFoundException.class, () -> service.execute(contaOrigem, contaDestino, valor), "Saldo da Conta 1 não encontrado");
    }

    @Test
    void executeLimiteIndisponivel() {
        Correntista correntistaOrigem = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta contaOrigem = createConta(1, 1, "1111", correntistaOrigem);
        Saldo saldoOrigem = createSaldo(1, contaOrigem, new BigDecimal("1000"));
        Correntista correntistaDestino = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Conta contaDestino = createConta(3, 3, "3333", correntistaDestino);
        Saldo saldoDestino = createSaldo(3, contaDestino, new BigDecimal("3000"));
        when(saldoRepository.findByConta(contaOrigem)).thenReturn(Optional.of(saldoOrigem));
        when(saldoRepository.findByConta(contaDestino)).thenReturn(Optional.of(saldoDestino));
        BigDecimal valor = new BigDecimal("1234");
        assertThrows(BusinessException.class, () -> service.execute(contaOrigem, contaDestino, valor), "Limite da Conta Número %d Agência %s insuficiente");
    }
}
