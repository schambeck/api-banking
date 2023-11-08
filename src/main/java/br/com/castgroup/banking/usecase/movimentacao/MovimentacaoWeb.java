package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.ContaWeb;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MovimentacaoWeb {
	private Integer id;
	private ContaWeb conta;
	private LocalDate data;
	private TipoMovimentacao tipo;
	private BigDecimal valor;
	private BigDecimal saldoAnterior;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
