package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindContaUseCase {
    private final ContaRepository repository;

    public Conta execute(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity %d not found".formatted(id)));
    }
}
