package br.com.castgroup.banking.usecase.transferencia;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DadosConta {
	private Integer numero;
	private String agencia;
}
