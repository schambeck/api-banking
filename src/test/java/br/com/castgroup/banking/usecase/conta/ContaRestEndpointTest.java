package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.correntista.CorrentistaWeb;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.conta.ContaWebUtil.createContaWeb;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ContaRestEndpointTest {
    @LocalServerPort
    int port;
    
    @MockBean
    ContaController controller;
    
    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }
    
    @Test
    void givenCreateValidValues_whenNotExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        Conta created = createConta(4, 4, "4444", correntistaMock);
        when(controller.create(toCreate)).thenReturn(created);
        
        CorrentistaWeb correntistaWeb = createCorrentistaWeb(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        ContaWeb payload = createContaWeb(4, "4444", correntistaWeb);
        given().
                contentType(JSON).
                accept(JSON).
                body(payload).
        when().
                post("/contas").
        then().
                log().all().
                statusCode(200).
                body("numero", is(4));
    }
    
    @Test
    void givenUpdateValidValues_whenExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toUpdate = createConta( 1, 1, "1112", correntistaMock);
        Conta updated = createConta(1, 1, "1112", correntistaMock);
        when(controller.update(1, toUpdate)).thenReturn(updated);
        
        CorrentistaWeb correntistaWeb = createCorrentistaWeb(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        ContaWeb payload = createContaWeb(1, 1, "1112", correntistaWeb);
        given().
                contentType(JSON).
                accept(JSON).
                body(payload).
        when().
                put("/contas/{id}", "1").
        then().
                log().all().
                statusCode(200).
                body("agencia", is("1112"));
    }
    
//    @Test
//    void givenUpdateInvalidId_whenNotExists_thenWrong() {
//        Correntista correntistaMock = createCorrentista(5, "Marion Vineyard", "marionvineyard@gmail.com", "66120072063");
//        Conta toUpdate = createConta(5, "5555", correntistaMock);
//        when(controller.update(5, toUpdate)).thenThrow(new NotFoundException("Entity %d not found".formatted(5)));
//
//        CorrentistaWeb correntistaWeb = createCorrentistaWeb(5, "Marion Vineyard", "marionvineyard@gmail.com", "66120072063");
//        ContaWeb payload = createContaWeb(5, "5555", correntistaWeb);
//        given().
//                contentType(JSON).
//                accept(JSON).
//                body(payload).
//        when().
//                put("/contas/{id}", "5").
//        then().
//                log().all().
//                statusCode(404);
//    }
    
    @Test
    void givenRemoveValidId_whenExists_thenCorrect() {
        doNothing().when(controller).remove(2);
        given().
                contentType(JSON).
        when().
                delete("/contas/{id}", "2").
        then().
                log().all().
                statusCode(204);
    }
    
    @Test
    void givenFind_whenExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta( 3, "3333", correntistaMock);
        when(controller.findById(3)).thenReturn(created);
        given().
                contentType(JSON).
        when().
                get("/contas/{id}", "3").
        then().
                log().all().
                statusCode(200).
                body("numero", equalTo(3));
    }
    
//    @Test
//    void givenFind_whenDoesNotExist_thenWrong() {
//        when(controller.findById(5)).thenThrow(new NotFoundException("Entity %d not found".formatted(5)));
//        get("/contas/{id}", "5")
//                .then()
//                .log().all()
//                .statusCode(404);
//    }
    
    @Test
    void givenSearch_whenExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(3, "3333", correntistaMock);
        when(controller.search(PageRequest.of(0, 10), "3")).thenReturn(new PageImpl<>(List.of(created)));
        given().
                contentType(JSON).
                queryParam("page", "0").
                queryParam("size", "10").
                queryParam("text", "3").
        when().
                get("/contas").
        then().
                log().all().
                statusCode(200).
                body("content", hasItem(allOf(hasEntry("numero", 3))));
    }
}
