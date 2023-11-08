package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.saldo.Saldo;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TransferenciaUtil {
    private TransferenciaUtil() {
    }
    
    public static Transferencia createTransferencia(Saldo saldoOrigem, Saldo saldoDestino, BigDecimal valor) {
        return createTransferencia(null, saldoOrigem, saldoDestino, valor);
    }
    public static Transferencia createTransferencia(Integer id, Saldo saldoOrigem, Saldo saldoDestino, BigDecimal valor) {
        return Transferencia.builder()
                .id(id)
                .saldoOrigem(saldoOrigem)
                .saldoDestino(saldoDestino)
                .data(LocalDate.now())
                .valor(valor)
                .build();
    }
}
