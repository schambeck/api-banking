package br.com.castgroup.banking.usecase.correntista;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Disabled
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CorrentistaRestEndpointIT {
    @LocalServerPort
    int port;
    
    @MockBean
    private CorrentistaController controller;
    
    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }
    
    @Test
    void givenCreateValidValues_whenNotExists_thenCorrect() {
        Correntista toCreate = createCorrentista("Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Correntista created = createCorrentista(4, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        when(controller.create(toCreate)).thenReturn(created);
        
        CorrentistaWeb payload = createCorrentistaWeb("Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        given().
                contentType(JSON).
                accept(JSON).
                body(payload).
        when().
                post("/correntistas").
        then().
                log().all().
                statusCode(200).
                body("nome", is("Burton McMurtry"));
    }
    
    @Test
    void givenUpdateValidValues_whenExists_thenCorrect() {
        Correntista toUpdate = createCorrentista("Scott Antonn", "scottanton@gmail.com", "73190252050");
        Correntista updated = createCorrentista(1, "Scott Antonn", "scottanton@gmail.com", "73190252050");
        when(controller.update(1, toUpdate)).thenReturn(updated);
        
        CorrentistaWeb payload = createCorrentistaWeb(1, "Scott Antonn", "scottanton@gmail.com", "73190252050");
        given().
                contentType(JSON).
                accept(JSON).
                body(payload).
        when().
                put("/correntistas/{id}", "1").
        then().
                log().all().
                statusCode(200).
                body("nome", is("Scott Antonn"));
    }
    
    @Test
    void givenRemoveValidId_whenExists_thenCorrect() {
        doNothing().when(controller).remove(2);
        given().
                contentType(JSON).
        when().
                delete("/correntistas/{id}", "2").
        then().
                log().all().
                statusCode(204);
    }
    
    @Test
    void givenFind_whenExists_thenCorrect() {
        Correntista created = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(controller.findById(3)).thenReturn(created);
        given().
                contentType(JSON).
        when().
                get("/correntistas/{id}", "3").
        then().
                log().all().
                statusCode(200).
                body("nome", equalTo("Charles Benson"));
    }
    
    @Test
    void givenSearch_whenExists_thenCorrect() {
        Correntista created = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(controller.search(PageRequest.of(0, 10), "Charles")).thenReturn(new PageImpl<>(List.of(created)));
        given().
                contentType(JSON).
                queryParam("page", "0").
                queryParam("size", "10").
                queryParam("text", "Charles").
        when().
                get("/correntistas").
        then().
                log().all().
                statusCode(200).
                body("content", hasItem(allOf(hasEntry("id", 3))));
    }
}
