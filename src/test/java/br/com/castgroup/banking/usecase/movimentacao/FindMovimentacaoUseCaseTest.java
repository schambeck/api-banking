package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindMovimentacaoUseCaseTest {
    @InjectMocks
    FindMovimentacaoUseCase findMovimentacao;
    
    @Mock
    MovimentacaoRepository movimentacaoRepository;
    
    @Test
    void execute() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta conta = createConta(3, "3333", correntistaMock);
        Movimentacao createdMock = createMovimentacao(3, conta, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        when(movimentacaoRepository.findById(3)).thenReturn(Optional.of(createdMock));
        Movimentacao created = findMovimentacao.execute(3);
        assertEquals(new BigDecimal("123"), created.getValor());
    }
}
