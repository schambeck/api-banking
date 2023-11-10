package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.saldo.SaldoWeb;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class SaqueWebUtil {
    private SaqueWebUtil() {
    }
    
    public static SaqueWeb createSaqueWeb(SaldoWeb saldo, BigDecimal valor) {
        return createSaqueWeb(null, saldo, valor);
    }
    public static SaqueWeb createSaqueWeb(Integer id, SaldoWeb saldo, BigDecimal valor) {
        return SaqueWeb.builder()
                .id(id)
                .saldo(saldo)
                .data(LocalDate.now())
                .valor(valor)
                .build();
    }
}
