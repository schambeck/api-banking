package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.exception.BusinessException;
import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.movimentacao.Movimentacao;
import br.com.castgroup.banking.usecase.movimentacao.MovimentacaoRepository;
import br.com.castgroup.banking.usecase.saldo.Saldo;
import br.com.castgroup.banking.usecase.saldo.SaldoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.CREDITO;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static br.com.castgroup.banking.usecase.transferencia.TransferenciaUtil.createTransferencia;

@Service
@RequiredArgsConstructor
public class CreateTransferenciaUseCase {
    private final TransferenciaRepository transferenciaRepository;
    private final SaldoRepository saldoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Transferencia execute(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        Saldo saldoOrigem = saldoRepository.findByConta(contaOrigem).orElseThrow(() -> new NotFoundException("Saldo da Conta %d não encontrado".formatted(contaOrigem.getId())));
        Saldo saldoDestino = saldoRepository.findByConta(contaDestino).orElseThrow(() -> new NotFoundException("Saldo da Conta %d não encontrado".formatted(contaDestino.getId())));
        if (saldoOrigem.getValor().compareTo(valor) < 0) {
            throw new BusinessException("Limite da Conta Número %d Agência %s insuficiente".formatted(saldoOrigem.getConta().getNumero(), saldoOrigem.getConta().getAgencia()));
        }
        saldoOrigem.setValor(saldoOrigem.getValor().subtract(valor));
        saldoDestino.setValor(saldoDestino.getValor().add(valor));
        saldoRepository.save(saldoOrigem);
        saldoRepository.save(saldoDestino);
        Transferencia transferencia = createTransferencia(saldoOrigem, saldoDestino, valor);
        Transferencia created = transferenciaRepository.save(transferencia);
        Movimentacao movimentacaoDebito = createMovimentacao(saldoOrigem.getConta(), transferencia.getData(), DEBITO, transferencia.getValor(), saldoOrigem.getValor());
        Movimentacao movimentacaoCredito = createMovimentacao(saldoOrigem.getConta(), transferencia.getData(), CREDITO, transferencia.getValor(), saldoDestino.getValor());
        movimentacaoRepository.save(movimentacaoDebito);
        movimentacaoRepository.save(movimentacaoCredito);
        return created;
    }
}
