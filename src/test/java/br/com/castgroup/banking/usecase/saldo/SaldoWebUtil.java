package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.ContaWeb;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public final class SaldoWebUtil {
    private SaldoWebUtil() {
    }
    
    public static SaldoWeb createSaldoWeb(ContaWeb conta) {
        return SaldoWeb.builder()
                .conta(conta)
                .valor(ZERO)
                .build();
    }
    
    public static SaldoWeb createSaldoWeb(ContaWeb conta, BigDecimal valor) {
        return createSaldoWeb(null, conta, valor);
    }
    
    public static SaldoWeb createSaldoWeb(Integer id, ContaWeb conta, BigDecimal valor) {
        return SaldoWeb.builder()
                .id(id)
                .conta(conta)
                .valor(valor)
                .build();
    }
}
