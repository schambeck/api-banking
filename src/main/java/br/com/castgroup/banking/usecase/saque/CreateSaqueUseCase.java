package br.com.castgroup.banking.usecase.saque;

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
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static br.com.castgroup.banking.usecase.saque.SaqueUtil.createSaque;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSaqueUseCase {
    private final SaqueRepository saqueRepository;
    private final SaldoRepository saldoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Saque execute(Conta conta, LocalDate data, BigDecimal valor) {
        log.debug("Saque started:");
        Saldo saldo = saldoRepository.lockById(conta.getNumero()).orElseThrow(() -> new NotFoundException("Saldo da Conta %d não encontrado".formatted(conta.getId())));
        log.debug("Conta {}/{} Data {} Valor {} Saldo {}", conta.getNumero(), conta.getAgencia(), data, valor, saldo.getValor());
        if (saldo.getValor().compareTo(valor) < 0) {
            throw new BusinessException("Limite da Conta Número %d Agência %s insuficiente".formatted(saldo.getConta().getNumero(), saldo.getConta().getAgencia()));
        }
        BigDecimal saldoAnterior = saldo.getValor();
        saldo.setValor(saldo.getValor().subtract(valor));
        saldoRepository.save(saldo);
        Saque saque = createSaque(saldo, data, valor);
        Saque created = saqueRepository.save(saque);
        Movimentacao movimentacao = createMovimentacao(saque.getSaldo().getConta(), saque.getData(), DEBITO, saque.getValor(), saldoAnterior);
        movimentacaoRepository.save(movimentacao);
        log.debug("Saque ended: Conta {}/{} Data {} Saldo Ant {} Valor {} Saldo {}", movimentacao.getConta().getNumero(), movimentacao.getConta().getAgencia(), movimentacao.getData(), movimentacao.getSaldoAnterior(), movimentacao.getValor(), saldo.getValor());
        return created;
    }
}
