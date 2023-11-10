package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import br.com.castgroup.banking.usecase.correntista.CorrentistaWeb;
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

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.conta.ContaWebUtil.createContaWeb;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
class ContaRestEndpointTest {
    @InjectMocks
    ContaRestEndpoint endpoint;
    
    @Mock
    ContaMapper mapper;
    
    @Mock
    ContaController controller;
    
    @Test
    void givenCreateValidValues_whenNotExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        Conta created = createConta(4, 4, "4444", correntistaMock);
        CorrentistaWeb correntistaWeb = createCorrentistaWeb(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        ContaWeb payload = createContaWeb(4, "4444", correntistaWeb);
        when(mapper.toEntity(payload)).thenReturn(toCreate);
        when(controller.createContaSaldoZerado(toCreate)).thenReturn(created);
        when(mapper.toWeb(created)).thenReturn(payload);
        
        ResponseEntity<ContaWeb> response = endpoint.create(payload);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().getNumero());
    }
    
    @Test
    void givenUpdateValidValues_whenExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toUpdate = createConta( 1, 1, "1112", correntistaMock);
        Conta updated = createConta(1, 1, "1112", correntistaMock);
        CorrentistaWeb correntistaWeb = createCorrentistaWeb(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        ContaWeb payload = createContaWeb(1, 1, "1112", correntistaWeb);
        when(mapper.toEntity(payload)).thenReturn(toUpdate);
        when(controller.update(1, toUpdate)).thenReturn(updated);
        when(mapper.toWeb(updated)).thenReturn(payload);
        
        ResponseEntity<ContaWeb> response = endpoint.update(1, payload);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1112", response.getBody().getAgencia());
    }
    
    @Test
    void givenRemoveValidId_whenExists_thenCorrect() {
        doNothing().when(controller).remove(2);
        ResponseEntity<Void> response = endpoint.remove(2);
        
        assertEquals(NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    void givenFind_whenExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta( 3, "3333", correntistaMock);
        CorrentistaWeb correntistaWebMock = createCorrentistaWeb(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        ContaWeb createdWeb = createContaWeb( 3, "3333", correntistaWebMock);
        when(controller.findById(3)).thenReturn(created);
        when(mapper.toWeb(created)).thenReturn(createdWeb);
        ResponseEntity<ContaWeb> response = endpoint.findById(3);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getNumero());
    }
    
    @Test
    void givenSearch_whenExists_thenCorrect() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta created = createConta(3, "3333", correntistaMock);
        CorrentistaWeb correntistaWebMock = createCorrentistaWeb(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        ContaWeb createdWeb = createContaWeb( 3, "3333", correntistaWebMock);
        when(controller.search(PageRequest.of(0, 10), "3")).thenReturn(new PageImpl<>(List.of(created)));
        when(mapper.toWeb(created)).thenReturn(createdWeb);
        ResponseEntity<Page<ContaWeb>> response = endpoint.search(PageRequest.of(0, 10), "3");
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getContent().get(0).getNumero());
    }
}
