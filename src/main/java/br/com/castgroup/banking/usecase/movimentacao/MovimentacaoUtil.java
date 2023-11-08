package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class MovimentacaoUtil {
    private MovimentacaoUtil() {
    }
    
    public static Movimentacao createMovimentacao(Conta conta, LocalDate data, TipoMovimentacao tipo, BigDecimal valor, BigDecimal saldoAnterior) {
        return createMovimentacao(null, conta, data, tipo, valor, saldoAnterior);
    }
    
    public static Movimentacao createMovimentacao(Integer id, Conta conta, LocalDate data, TipoMovimentacao tipo, BigDecimal valor, BigDecimal saldoAnterior) {
        return Movimentacao.builder()
                .id(id)
                .conta(conta)
                .data(data)
                .tipo(tipo)
                .valor(valor)
                .saldoAnterior(saldoAnterior)
                .build();
    }
}
