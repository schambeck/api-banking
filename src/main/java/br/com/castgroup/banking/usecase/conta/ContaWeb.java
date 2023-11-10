package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.CorrentistaWeb;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContaWeb {
	private Integer id;
	private Integer numero;
	private String agencia;
	private CorrentistaWeb correntista;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
