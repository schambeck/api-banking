package br.com.castgroup.banking.usecase.movimentacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMovimentacaoUseCase {
    private final MovimentacaoRepository repository;

    @Transactional
    public Movimentacao execute(Movimentacao entity) {
//        Movimentacao movimentacaoDebito = createMovimentacaoDebito(valor, contaOrigem);
//        Movimentacao movimentacaoCredito = createMovimentacaoCredito(valor, contaDestino);
        return repository.save(entity);
    }
    
//    private static Movimentacao createMovimentacaoDebito(BigDecimal valor, Conta contaOrigem) {
//        return Movimentacao.builder()
//                .data(LocalDate.now())
//                .conta(contaOrigem)
//                .tipo(DEBITO)
//                .valor(valor)
//                .build();
//    }

//    private static Movimentacao createMovimentacaoCredito(BigDecimal valor, Conta contaOrigem) {
//        return Movimentacao.builder()
//                .data(LocalDate.now())
//                .conta(contaOrigem)
//                .tipo(CREDITO)
//                .valor(valor)
//                .build();
//    }
}
