package br.com.castgroup.banking.usecase.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchContaUseCase {
    private final ContaRepository repository;
    
    public Page<Conta> execute(Pageable pageable, String text) {
        if (text == null || text.isBlank()) {
            return repository.findAllByOrderByIdAsc(pageable);
        } else {
            return repository.findAllByNumeroOrderByIdAsc(pageable, Integer.valueOf(text));
        }
    }
}
