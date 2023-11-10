package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindContaByNumeroAndAgenciaUseCase {
    private final ContaRepository repository;

    public Conta execute(int numero, String agencia) {
        return repository.findByNumeroAndAgencia(numero, agencia).orElseThrow(() -> new NotFoundException("Conta Número %d e Agência %s não encontrada".formatted(numero, agencia)));
    }
}
