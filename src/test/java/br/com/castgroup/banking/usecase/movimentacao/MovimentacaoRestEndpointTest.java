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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
class MovimentacaoRestEndpointTest {
    @InjectMocks
    MovimentacaoRestEndpoint endpoint;
    
    @Mock
    MovimentacaoController controller;
    
    @Test
    void extrato() {
        Correntista correntistaMock = createCorrentista(1, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(1, "1111", correntistaMock);
        Movimentacao movimentacao1 = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        Movimentacao movimentacao2 = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("321"), new BigDecimal("123"));
        when(controller.extrato(PageRequest.of(0, 10), 1)).thenReturn(new PageImpl<>(List.of(movimentacao1, movimentacao2)));
        ResponseEntity<Page<MovimentacaoWeb>> response = endpoint.extrato(PageRequest.of(0, 10), 1);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("123"), response.getBody().getContent().get(0).getValor());
        assertEquals(new BigDecimal("321"), response.getBody().getContent().get(1).getValor());
    }

    @Test
    void extratoByNumeroContaAndAgencia() {
        Correntista correntistaMock = createCorrentista(1, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(1, "1111", correntistaMock);
        Movimentacao movimentacao1 = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        Movimentacao movimentacao2 = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("321"), new BigDecimal("123"));
        when(controller.extrato(PageRequest.of(0, 10), 1, "1111")).thenReturn(new PageImpl<>(List.of(movimentacao1, movimentacao2)));
        ResponseEntity<Page<MovimentacaoWeb>> response = endpoint.extrato(PageRequest.of(0, 10), 1, "1111");
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("123"), response.getBody().getContent().get(0).getValor());
        assertEquals(new BigDecimal("321"), response.getBody().getContent().get(1).getValor());
    }

    @Test
    void findById() {
        Correntista correntistaMock = createCorrentista(1, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(1, "1111", correntistaMock);
        Movimentacao movimentacao = createMovimentacao(created, LocalDate.parse("2023-11-01"), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        when(controller.findById(1)).thenReturn(movimentacao);
        ResponseEntity<MovimentacaoWeb> response = endpoint.get(1);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("123"), response.getBody().getValor());
    }
}
