package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
class CorrentistaRestEndpointTest {
    @InjectMocks
    CorrentistaRestEndpoint endpoint;
    
    @Mock
    private CorrentistaController controller;
    
    @Test
    void givenCreateValidValues_whenNotExists_thenCorrect() {
        Correntista toCreate = createCorrentista("Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Correntista created = createCorrentista(4, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        when(controller.create(toCreate)).thenReturn(created);
        
        CorrentistaWeb payload = createCorrentistaWeb("Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        ResponseEntity<CorrentistaWeb> response = endpoint.create(payload);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Burton McMurtry", response.getBody().getNome());
    }
    
    @Test
    void givenUpdateValidValues_whenExists_thenCorrect() {
        Correntista toUpdate = createCorrentista("Scott Antonn", "scottanton@gmail.com", "73190252050");
        Correntista updated = createCorrentista(1, "Scott Antonn", "scottanton@gmail.com", "73190252050");
        when(controller.update(1, toUpdate)).thenReturn(updated);
        
        CorrentistaWeb payload = createCorrentistaWeb(1, "Scott Antonn", "scottanton@gmail.com", "73190252050");
        ResponseEntity<CorrentistaWeb> response = endpoint.update(1, payload);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Scott Antonn", response.getBody().getNome());
    }

    @Test
    void givenRemoveValidId_whenExists_thenCorrect() {
        ResponseEntity<Void> response = endpoint.remove(2);
        
        assertEquals(NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    void givenFind_whenExists_thenCorrect() {
        Correntista created = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(controller.findById(3)).thenReturn(created);
        ResponseEntity<CorrentistaWeb> response = endpoint.get(3);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Charles Benson", response.getBody().getNome());
    }

    @Test
    void givenSearch_whenExists_thenCorrect() {
        Correntista created = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(controller.search(PageRequest.of(0, 10), "Charles")).thenReturn(new PageImpl<>(List.of(created)));
        
        ResponseEntity<Page<CorrentistaWeb>> response = endpoint.search(PageRequest.of(0, 10), "Charles");
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getContent().get(0).getId());
    }
}
