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

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateMovimentacaoUseCaseTest {
    @InjectMocks
    CreateMovimentacaoUseCase createMovimentacao;
    
    @Mock
    MovimentacaoRepository repository;
    
    @Test
    void execute() {
        Correntista correntistaMock = createCorrentista(1, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta conta = createConta(1, "1111", correntistaMock);
        Movimentacao toCreate = createMovimentacao(conta, LocalDate.now(), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        Movimentacao createdMock = createMovimentacao(1, conta, LocalDate.now(), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        when(repository.save(toCreate)).thenReturn(createdMock);
        
        Movimentacao created = createMovimentacao.execute(toCreate);
        
        assertEquals(new BigDecimal("123"), created.getValor());
    }
}
