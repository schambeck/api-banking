package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.saldo.Saldo;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class SaqueUtil {
    private SaqueUtil() {
    }
    
    public static Saque createSaque(Saldo saldo, BigDecimal valor) {
        return createSaque(null, saldo, valor);
    }
    public static Saque createSaque(Integer id, Saldo saldo, BigDecimal valor) {
        return Saque.builder()
                .id(id)
                .saldo(saldo)
                .data(LocalDate.now())
                .valor(valor)
                .build();
    }
}
