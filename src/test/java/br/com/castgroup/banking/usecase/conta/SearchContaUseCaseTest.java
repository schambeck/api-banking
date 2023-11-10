package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchContaUseCaseTest {
    @InjectMocks
    private SearchContaUseCase service;
    
    @Mock
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

    @Test
    void searchEmpty() {
        when(repository.findAllByNumeroOrderByIdAsc(PageRequest.of(0, 10), null)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Conta> contas = service.execute(PageRequest.of(0, 10), "");
        assertNull(contas);
    }
}
