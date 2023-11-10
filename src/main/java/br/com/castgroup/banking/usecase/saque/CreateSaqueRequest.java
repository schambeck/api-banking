package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.transferencia.DadosConta;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class CreateSaqueRequest {
	private DadosConta dadosConta;
	private LocalDate data;
	private BigDecimal valor;
}
