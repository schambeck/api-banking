package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
public class CreateSaldoZeradoUseCase {
    private final SaldoRepository saldoRepository;

    @Transactional
    public Saldo execute(Conta conta) {
        Optional<Saldo> saldo = saldoRepository.findByConta(conta);
        if (saldo.isPresent()) {
            throw new NotFoundException("Saldo Conta %d já existe".formatted(conta.getId()));
        }
        Saldo toCreate = Saldo.builder()
                .conta(conta)
                .valor(ZERO)
                .build();
        return saldoRepository.save(toCreate);
    }
}
