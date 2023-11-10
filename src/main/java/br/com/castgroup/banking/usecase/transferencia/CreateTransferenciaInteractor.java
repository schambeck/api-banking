package br.com.castgroup.banking.usecase.transferencia;

import br.com.castgroup.banking.usecase.conta.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateTransferenciaInteractor {
    private final CreateTransferenciaUseCase createTransferencia;
//    private final ContaRepository contaRepository;

//    @Transactional
    public Transferencia execute(DadosConta dadosContaOrigem, DadosConta dadosContaDestino, LocalDate data, BigDecimal valor) {
//        Conta contaOrigem = contaRepository.findByNumeroAndAgencia(dadosContaOrigem.getNumero(), dadosContaOrigem.getAgencia()).orElseThrow(() -> new NotFoundException("Conta %d e agência %s não encontrada".formatted(dadosContaOrigem.getNumero(), dadosContaOrigem.getAgencia())));
//        Conta contaDestino = contaRepository.findByNumeroAndAgencia(dadosContaDestino.getNumero(), dadosContaDestino.getAgencia()).orElseThrow(() -> new NotFoundException("Conta %d e agência %s não encontrada".formatted(dadosContaDestino.getNumero(), dadosContaDestino.getAgencia())));
//        Conta contaOrigem = contaRepository.findById(dadosContaOrigem.getNumero()).orElseThrow(() -> new NotFoundException("Conta %d e agência %s não encontrada".formatted(dadosContaOrigem.getNumero(), dadosContaOrigem.getAgencia())));
//        Conta contaDestino = contaRepository.findById(dadosContaDestino.getNumero()).orElseThrow(() -> new NotFoundException("Conta %d e agência %s não encontrada".formatted(dadosContaDestino.getNumero(), dadosContaDestino.getAgencia())));
        Conta contaOrigem = Conta.builder().numero(dadosContaOrigem.getNumero()).build();
        Conta contaDestino = Conta.builder().numero(dadosContaDestino.getNumero()).build();
        return createTransferencia.execute(contaOrigem, contaDestino, data, valor);
    }
}
