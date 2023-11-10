package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.saldo.SaldoWeb;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TransferenciaWeb {
	private Integer id;
	private SaldoWeb saldoOrigem;
	private SaldoWeb saldoDestino;
	private LocalDate data;
	private BigDecimal valor;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
