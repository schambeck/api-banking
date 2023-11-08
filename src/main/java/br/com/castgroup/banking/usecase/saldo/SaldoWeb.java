package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.ContaWeb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SaldoWeb {
	private int id;
	private ContaWeb conta;
	private BigDecimal valor;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
