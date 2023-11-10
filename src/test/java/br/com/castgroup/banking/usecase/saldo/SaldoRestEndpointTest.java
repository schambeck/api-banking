package br.com.castgroup.banking.usecase.saldo;

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
import java.util.List;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.saldo.SaldoUtil.createSaldo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
class SaldoRestEndpointTest {
    @InjectMocks
    SaldoRestEndpoint endpoint;
    
    @Mock
    SaldoController controller;
    
    @Test
    void findById() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta contaMock = createConta( 3, "3333", correntistaMock);
        Saldo created = createSaldo(3, contaMock, new BigDecimal("3000"));
        when(controller.findById(3)).thenReturn(created);
        ResponseEntity<SaldoWeb> response = endpoint.findById(3);
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("3000"), response.getBody().getValor());
    }
    
    @Test
    void search() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta contaMock = createConta( 3, "3333", correntistaMock);
        Saldo created = createSaldo(3, contaMock, new BigDecimal("3000"));
        when(controller.search(PageRequest.of(0, 10), "3")).thenReturn(new PageImpl<>(List.of(created)));
        ResponseEntity<Page<SaldoWeb>> response = endpoint.search(PageRequest.of(0, 10), "3");
        
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("3000"), response.getBody().getContent().get(0).getValor());
    }
}
