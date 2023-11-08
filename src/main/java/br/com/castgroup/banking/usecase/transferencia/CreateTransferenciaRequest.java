package br.com.castgroup.banking.usecase.transferencia;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateTransferenciaRequest {
	private DadosConta dadosContaOrigem;
	private DadosConta dadosContaDestino;
	private BigDecimal valor;
}
