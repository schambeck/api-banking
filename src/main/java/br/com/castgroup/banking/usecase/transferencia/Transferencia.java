package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.saldo.Saldo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Entity
@Table(name = "transferencia")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transferencia {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "saldo_origem_id", referencedColumnName = "id")
	private Saldo saldoOrigem;

	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "saldo_destino_id", referencedColumnName = "id")
	private Saldo saldoDestino;

	@Column(name = "data")
	private LocalDate data;

	@Column(name = "valor")
	private BigDecimal valor;

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
