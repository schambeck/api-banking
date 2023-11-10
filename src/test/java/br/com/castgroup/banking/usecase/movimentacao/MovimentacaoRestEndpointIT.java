package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.Conta;
import br.com.castgroup.banking.usecase.correntista.Correntista;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Disabled
@SpringBootTest(webEnvironment = RANDOM_PORT)
class MovimentacaoRestEndpointIT {
    @LocalServerPort
    int port;
    
    @MockBean
    MovimentacaoController controller;
    
    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }
    
    @Test
    void extrato() {
        Correntista correntistaMock = createCorrentista(1, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(1, "1111", correntistaMock);
        Movimentacao movimentacao1 = createMovimentacao(created, LocalDate.now(), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        Movimentacao movimentacao2 = createMovimentacao(created, LocalDate.now(), DEBITO, new BigDecimal("321"), new BigDecimal("123"));
        when(controller.extrato(PageRequest.of(0, 10), 1)).thenReturn(new PageImpl<>(List.of(movimentacao1, movimentacao2)));
        given().
                contentType(JSON).
                queryParam("page", "0").
                queryParam("size", "10").
        when().
                get("/movimentacoes/{contaId}", "1").
        then().
                log().all().
                statusCode(200).
                body("content", hasItem(allOf(hasEntry("valor", 123)))).
                body("content", hasItem(allOf(hasEntry("valor", 321))));
    }
}
