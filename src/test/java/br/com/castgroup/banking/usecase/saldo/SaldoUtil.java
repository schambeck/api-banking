package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.Conta;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public final class SaldoUtil {
    private SaldoUtil() {
    }
    
    public static Saldo createSaldo(Conta conta) {
        return Saldo.builder()
                .conta(conta)
                .valor(ZERO)
                .build();
    }
    
    public static Saldo createSaldo(Conta conta, BigDecimal valor) {
        return createSaldo(null, conta, valor);
    }
    
    public static Saldo createSaldo(Integer id, Conta conta, BigDecimal valor) {
        return Saldo.builder()
                .id(id)
                .conta(conta)
                .valor(valor)
                .build();
    }
}
