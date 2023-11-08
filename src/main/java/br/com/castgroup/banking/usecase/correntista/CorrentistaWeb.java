package br.com.castgroup.banking.usecase.correntista;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CorrentistaWeb {
	private Integer id;
	private String nome;
	private String email;
	private String cpf;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
