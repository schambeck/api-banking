package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.saldo.CreateSaldoZeradoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateContaSaldoZeradoInteractor {
    private final CreateContaUseCase createConta;
    private final CreateSaldoZeradoUseCase createSaldoZerado;

    @Transactional
    public Conta execute(Conta entity) {
        Conta conta = createConta.execute(entity);
        createSaldoZerado.execute(conta);
        return conta;
    }
}
