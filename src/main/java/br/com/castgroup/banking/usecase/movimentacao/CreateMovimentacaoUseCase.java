package br.com.castgroup.banking.usecase.movimentacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMovimentacaoUseCase {
    private final MovimentacaoRepository repository;

    @Transactional
    public Movimentacao execute(Movimentacao entity) {
        return repository.save(entity);
    }
}
