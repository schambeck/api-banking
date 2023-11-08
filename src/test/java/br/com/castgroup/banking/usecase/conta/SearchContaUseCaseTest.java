package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SearchContaUseCase.class)
class SearchContaUseCaseTest {
    @Autowired
    private SearchContaUseCase service;
    
    @MockBean
    private ContaRepository repository;
    
    @Test
    void search() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta createdMock = createConta(3, "3333", correntistaMock);
        when(repository.findAllByNumeroOrderByIdAsc(PageRequest.of(0, 10), 3)).thenReturn(new PageImpl<>(singletonList(createdMock)));
        Page<Conta> contas = service.execute(PageRequest.of(0, 10), "3");
        assertEquals(1, contas.getContent().size());
        assertEquals(3, contas.getContent().get(0).getNumero());
    }

    @Test
    void searchNull() {
        when(repository.findAllByNumeroOrderByIdAsc(PageRequest.of(0, 10), null)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Conta> contas = service.execute(PageRequest.of(0, 10), null);
        assertNull(contas);
    }
}
