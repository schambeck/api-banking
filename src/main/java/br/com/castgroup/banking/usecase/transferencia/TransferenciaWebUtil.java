package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.saldo.SaldoWeb;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TransferenciaWebUtil {
    private TransferenciaWebUtil() {
    }
    
    public static TransferenciaWeb createTransferenciaWeb(SaldoWeb saldoOrigem, SaldoWeb saldoDestino, BigDecimal valor) {
        return createTransferenciaWeb(null, saldoOrigem, saldoDestino, valor);
    }
    public static TransferenciaWeb createTransferenciaWeb(Integer id, SaldoWeb saldoOrigem, SaldoWeb saldoDestino, BigDecimal valor) {
        return TransferenciaWeb.builder()
                .id(id)
                .saldoOrigem(saldoOrigem)
                .saldoDestino(saldoDestino)
                .data(LocalDate.parse("2023-11-01"))
                .valor(valor)
                .build();
    }
}
