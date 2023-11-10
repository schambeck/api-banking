package br.com.castgroup.banking.usecase.saldo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchSaldoUseCase {
    private final SaldoRepository repository;
    
    public Page<Saldo> execute(Pageable pageable, String text) {
        if (text == null || text.isBlank()) {
            return repository.findAllByOrderById(pageable);
        } else {
            return repository.findAllByConta_NumeroOrderByIdAsc(pageable, Integer.valueOf(text));
        }
    }
}
