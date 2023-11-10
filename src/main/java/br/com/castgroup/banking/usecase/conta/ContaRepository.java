package br.com.castgroup.banking.usecase.conta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
    Page<Conta> findAllByOrderById(Pageable pageable);

    Page<Conta> findAllByNumeroOrderByIdAsc(Pageable pageable, Integer numero);
    
    Optional<Conta> findByNumeroAndAgencia(Integer numero, String agencia);
}
