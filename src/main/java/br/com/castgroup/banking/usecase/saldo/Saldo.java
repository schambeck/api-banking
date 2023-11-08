package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.Conta;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@Entity
@Table(name = "saldo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Saldo {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	@EqualsAndHashCode.Include
	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "conta_id", referencedColumnName = "id")
	private Conta conta;

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
