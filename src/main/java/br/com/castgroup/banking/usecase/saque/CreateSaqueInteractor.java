package br.com.castgroup.banking.usecase.saque;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.transferencia.DadosConta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateSaqueInteractor {
    private final CreateSaqueUseCase createSaque;
//    private final ContaRepository contaRepository;

//    @Transactional
    public Saque execute(DadosConta dadosConta, LocalDate data, BigDecimal valor) {
//        Conta conta = contaRepository.findByNumeroAndAgencia(dadosConta.getNumero(), dadosConta.getAgencia()).orElseThrow(() -> new NotFoundException("Conta %d e agência %s não encontrada".formatted(dadosConta.getNumero(), dadosConta.getAgencia())));
        Conta conta = Conta.builder().numero(dadosConta.getNumero()).build();
        return createSaque.execute(conta, data, valor);
    }
}
