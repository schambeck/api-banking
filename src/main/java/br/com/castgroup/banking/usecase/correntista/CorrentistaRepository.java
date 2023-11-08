package br.com.castgroup.banking.usecase.correntista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrentistaRepository extends JpaRepository<Correntista, Integer> {
    Page<Correntista> findAllByOrderByIdAsc(Pageable pageable);

    Page<Correntista> findAllByNomeContainingIgnoreCaseOrderByIdAsc(Pageable pageable, String nome);
}
