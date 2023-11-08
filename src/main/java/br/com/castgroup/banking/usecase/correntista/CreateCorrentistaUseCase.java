package br.com.castgroup.banking.usecase.correntista;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCorrentistaUseCase {
    private final CorrentistaRepository repository;

    @Transactional
    public Correntista execute(Correntista entity) {
        return repository.save(entity);
    }
}
