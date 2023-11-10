package br.com.castgroup.banking.usecase.saque;

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
public class SaqueWeb {
	private Integer id;
	private SaldoWeb saldo;
	private LocalDate data;
	private BigDecimal valor;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
