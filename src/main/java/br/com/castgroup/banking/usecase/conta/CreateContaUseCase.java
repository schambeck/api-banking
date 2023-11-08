package br.com.castgroup.banking.usecase.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateContaUseCase {
    private final ContaRepository repository;

    @Transactional
    public Conta execute(Conta entity) {
        return repository.save(entity);
    }
}
