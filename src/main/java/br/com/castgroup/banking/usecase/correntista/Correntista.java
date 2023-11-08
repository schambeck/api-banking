package br.com.castgroup.banking.usecase.correntista;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@Entity
@Table(name = "correntista")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Correntista {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@EqualsAndHashCode.Include
	@Column(name = "cpf")
	private String cpf;

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
