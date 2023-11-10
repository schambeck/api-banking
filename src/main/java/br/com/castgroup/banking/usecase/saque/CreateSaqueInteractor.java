package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.exception.NotFoundException;
import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.conta.ContaRepository;
import br.com.castgroup.banking.usecase.transferencia.DadosConta;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateSaqueInteractor {
    private final CreateSaqueUseCase createSaque;
    private final ContaRepository contaRepository;

    @Transactional
    public Saque execute(DadosConta dadosConta, LocalDate data, BigDecimal valor) {
        Conta conta = contaRepository.findByNumeroAndAgencia(dadosConta.getNumero(), dadosConta.getAgencia()).orElseThrow(() -> new NotFoundException("Conta %d e agência %s não encontrada".formatted(dadosConta.getNumero(), dadosConta.getAgencia())));
        return createSaque.execute(conta, data, valor);
    }
}
