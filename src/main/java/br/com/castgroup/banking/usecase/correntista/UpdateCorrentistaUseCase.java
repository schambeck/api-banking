package br.com.castgroup.banking.usecase.correntista;

import br.com.castgroup.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCorrentistaUseCase {
    private final CorrentistaRepository repository;

    @Transactional
    public Correntista execute(int id, Correntista entity) {
        Correntista toUpdate = findById(id);
        CorrentistaMapper.INSTANCE.copy(entity, toUpdate);
        return repository.save(toUpdate);
    }

    private Correntista findById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity %d not found".formatted(id)));
    }
}
