package br.com.castgroup.banking.usecase.correntista;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveCorrentistaUseCase {
    private final CorrentistaRepository repository;

    @Transactional
    public void execute(int id) {
        repository.deleteById(id);
    }
}
