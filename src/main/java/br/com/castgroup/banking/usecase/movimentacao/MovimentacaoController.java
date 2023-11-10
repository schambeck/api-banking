package br.com.castgroup.banking.usecase.movimentacao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimentacaoController {
    private final FindMovimentacaoUseCase find;
    private final ExtratoMovimentacaoUseCase extrato;
    
    public Movimentacao findById(int id) {
        return find.execute(id);
    }

    public Page<Movimentacao> extrato(Pageable pageable, int contaId) {
        return extrato.execute(pageable, contaId);
    }
    
    public Page<Movimentacao> extrato(Pageable pageable, int numeroConta, String agenciaConta) {
        return extrato.execute(pageable, numeroConta, agenciaConta);
    }
}
