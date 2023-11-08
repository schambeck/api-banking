package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindSaldoByContaUseCase {
    private final SaldoRepository repository;

    public Saldo execute(int contaId) {
        return repository.findByConta_Id(contaId)
                .orElseThrow(() -> new NotFoundException("Saldo da Conta %d não encontrado".formatted(contaId)));
    }
}
