package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SearchCorrentistaUseCase.class)
class SearchCorrentistaUseCaseTest {
    @Autowired
    private SearchCorrentistaUseCase service;
    
    @MockBean
    private CorrentistaRepository repository;
    
    @Test
    void search() {
        Correntista createdMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(repository.findAllByNomeContainingIgnoreCaseOrderByIdAsc(PageRequest.of(0, 10), "Charles")).thenReturn(new PageImpl<>(singletonList(createdMock)));
        Page<Correntista> correntistas = service.execute(PageRequest.of(0, 10), "Charles");
        assertEquals(1, correntistas.getContent().size());
        assertEquals("Charles Benson", correntistas.getContent().get(0).getNome());
    }

    @Test
    void searchNull() {
        when(repository.findAllByNomeContainingIgnoreCaseOrderByIdAsc(PageRequest.of(0, 10), null)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Correntista> correntistas = service.execute(PageRequest.of(0, 10), null);
        assertNull(correntistas);
    }

    @Test
    void searchEmpty() {
        when(repository.findAllByNomeContainingIgnoreCaseOrderByIdAsc(PageRequest.of(0, 10), "")).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Correntista> correntistas = service.execute(PageRequest.of(0, 10), "");
        assertNull(correntistas);
    }
}
