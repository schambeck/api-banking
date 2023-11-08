package br.com.castgroup.banking.usecase.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveContaUseCase {
    private final ContaRepository repository;

    @Transactional
    public void execute(int id) {
        repository.deleteById(id);
    }
}
