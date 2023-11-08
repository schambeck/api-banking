package br.com.castgroup.banking.usecase.movimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {
    Page<Movimentacao> findAllByConta_IdOrderById(Pageable pageable, int contaId);
}
