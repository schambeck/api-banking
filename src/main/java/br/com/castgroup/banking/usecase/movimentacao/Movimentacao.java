package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.Conta;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@Entity
@Table(name = "movimentacao")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Movimentacao {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@EqualsAndHashCode.Include
	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "conta_id", referencedColumnName = "id")
	private Conta conta;
	
	@EqualsAndHashCode.Include
	@Column(name = "data")
	private LocalDate data;
	
	@EqualsAndHashCode.Include
	@Enumerated(STRING)
	@Column(name = "tipo")
	private TipoMovimentacao tipo;
	
	@EqualsAndHashCode.Include
	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "saldo_anterior")
	private BigDecimal saldoAnterior;

	@Version
	@Column(name = "version")
	private Integer version;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;
}
