package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ExtratoMovimentacaoUseCaseTest {
    @InjectMocks
    ExtratoMovimentacaoUseCase extratoMovimentacao;
    
    @Mock
    MovimentacaoRepository repository;
    
    @Test
    void execute() {
        Correntista correntistaMock = createCorrentista(1, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(1, "1111", correntistaMock);
        Movimentacao movimentacao1 = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        Movimentacao movimentacao2 = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("321"), new BigDecimal("123"));
        Page<Movimentacao> movimentacoes = new PageImpl<>(List.of(movimentacao1, movimentacao2));
        when(repository.findAllByConta_IdOrderById(PageRequest.of(0, 10), 3)).thenReturn(movimentacoes);
        
        Page<Movimentacao> extrato = extratoMovimentacao.execute(PageRequest.of(0, 10), 3);
        assertEquals(new BigDecimal("123"), extrato.getContent().get(0).getValor());
        assertEquals(new BigDecimal("321"), extrato.getContent().get(1).getValor());
    }
}
