package br.com.castgroup.banking.usecase.saldo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaldoController {
    private final FindSaldoUseCase find;
    private final SearchSaldoUseCase search;

    public Saldo findById(int id) {
        return find.execute(id);
    }

    public Page<Saldo> search(Pageable pageable, String text) {
        return search.execute(pageable, text);
    }
}
