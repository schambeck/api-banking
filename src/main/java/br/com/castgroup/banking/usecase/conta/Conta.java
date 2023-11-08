package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@Entity
@Table(name = "conta")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Conta {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@EqualsAndHashCode.Include
	@Column(name = "numero")
	private Integer numero;
	
	@EqualsAndHashCode.Include
	@Column(name = "agencia")
	private String agencia;

	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "correntista_id", referencedColumnName = "id")
	private Correntista correntista;

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
