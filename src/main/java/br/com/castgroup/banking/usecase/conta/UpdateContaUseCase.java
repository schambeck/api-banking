package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateContaUseCase {
    private final ContaRepository repository;
    private final ContaMapper mapper;

    @Transactional
    public Conta execute(int id, Conta entity) {
        Conta toUpdate = findById(id);
        mapper.copy(entity, toUpdate);
        return repository.save(toUpdate);
    }

    private Conta findById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity %d not found".formatted(id)));
    }
}
