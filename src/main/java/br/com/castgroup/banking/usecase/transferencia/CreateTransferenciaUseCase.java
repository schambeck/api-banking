package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.exception.BusinessException;
import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.movimentacao.Movimentacao;
import br.com.castgroup.banking.usecase.movimentacao.MovimentacaoRepository;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.saldo.SaldoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.CREDITO;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTransferenciaUseCase {
    private final TransferenciaRepository transferenciaRepository;
    private final SaldoRepository saldoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Transferencia execute(Conta contaOrigem, Conta contaDestino, LocalDate data, BigDecimal valor) {
        log.debug("Transferencia started:");
        Saldo saldoOrigem = saldoRepository.lockById(contaOrigem.getNumero()).orElseThrow(() -> new NotFoundException("Saldo da Conta %d não encontrado".formatted(contaOrigem.getId())));
        Saldo saldoDestino = saldoRepository.lockById(contaDestino.getNumero()).orElseThrow(() -> new NotFoundException("Saldo da Conta %d não encontrado".formatted(contaDestino.getId())));
        log.debug("Conta Origem  {}/{} Data {} Valor {} Saldo {}", contaOrigem.getNumero(), contaOrigem.getAgencia(), data, valor, saldoOrigem.getValor());
        log.debug("Conta Destino {}/{} Data {} Valor {} Saldo {}", contaDestino.getNumero(), contaDestino.getAgencia(), data, valor, saldoDestino.getValor());
        if (saldoOrigem.getValor().compareTo(valor) < 0) {
            throw new BusinessException("Limite da Conta Número %d Agência %s insuficiente".formatted(saldoOrigem.getConta().getNumero(), saldoOrigem.getConta().getAgencia()));
        }
        BigDecimal saldoAnteriorOrigem = saldoOrigem.getValor();
        BigDecimal saldoAnteriorDestino = saldoDestino.getValor();
        saldoOrigem.setValor(saldoOrigem.getValor().subtract(valor));
        saldoDestino.setValor(saldoDestino.getValor().add(valor));
        saldoRepository.save(saldoOrigem);
        saldoRepository.save(saldoDestino);
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, data, valor);
        Transferencia created = transferenciaRepository.save(transferencia);
        Movimentacao movimentacaoDebito = createMovimentacao(saldoOrigem.getConta(), transferencia.getData(), DEBITO, transferencia.getValor(), saldoAnteriorOrigem);
        Movimentacao movimentacaoCredito = createMovimentacao(saldoDestino.getConta(), transferencia.getData(), CREDITO, transferencia.getValor(), saldoAnteriorDestino);
        movimentacaoRepository.save(movimentacaoDebito);
        movimentacaoRepository.save(movimentacaoCredito);
        log.debug("Transferencia ended:");
        log.debug("Conta Debito  {}/{} Data {} Saldo Ant {} Valor {} Saldo {}", movimentacaoDebito.getConta().getNumero(), movimentacaoDebito.getConta().getAgencia(), movimentacaoDebito.getData(), movimentacaoDebito.getSaldoAnterior(), movimentacaoDebito.getValor(), saldoOrigem.getValor());
        log.debug("Conta Credito {}/{} Data {} Saldo Ant {} Valor {} Saldo {}", movimentacaoCredito.getConta().getNumero(), movimentacaoCredito.getConta().getAgencia(), movimentacaoCredito.getData(), movimentacaoCredito.getSaldoAnterior(), movimentacaoCredito.getValor(), saldoDestino.getValor());
        return created;
    }
}
