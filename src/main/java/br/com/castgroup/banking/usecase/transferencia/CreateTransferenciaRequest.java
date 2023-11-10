package br.com.castgroup.banking.usecase.transferencia;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class CreateTransferenciaRequest {
	private DadosConta dadosContaOrigem;
	private DadosConta dadosContaDestino;
	private LocalDate data;
	private BigDecimal valor;
}
