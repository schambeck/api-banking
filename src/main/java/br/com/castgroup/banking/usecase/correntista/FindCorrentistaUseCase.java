package br.com.castgroup.banking.usecase.correntista;

import br.com.castgroup.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindCorrentistaUseCase {
    private final CorrentistaRepository repository;

    public Correntista execute(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity %d not found".formatted(id)));
    }
}
