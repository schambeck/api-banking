package br.com.castgroup.banking.usecase.movimentacao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtratoMovimentacaoUseCase {
    private final MovimentacaoRepository repository;
    
    public Page<Movimentacao> execute(Pageable pageable, int contaId) {
        return repository.findAllByConta_IdOrderById(pageable, contaId);
    }
    
    public Page<Movimentacao> execute(Pageable pageable, int numeroConta, String agenciaConta) {
        return repository.findAllByConta_NumeroAndConta_AgenciaOrderById(pageable, numeroConta, agenciaConta);
    }
}
