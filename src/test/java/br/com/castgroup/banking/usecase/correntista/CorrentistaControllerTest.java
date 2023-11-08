package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CorrentistaController.class)
class CorrentistaControllerTest {
    @Autowired
    private CorrentistaController controller;
    
    @MockBean
    private CreateCorrentistaUseCase create;
    
    @MockBean
    private UpdateCorrentistaUseCase update;
    
    @MockBean
    private RemoveCorrentistaUseCase remove;
    
    @MockBean
    private FindCorrentistaUseCase find;
    
    @MockBean
    private SearchCorrentistaUseCase search;
    
    @Test
    void create() {
        Correntista toCreate = createCorrentista("Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Correntista createdMock = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        when(create.execute(toCreate)).thenReturn(createdMock);
        Correntista created = controller.create(toCreate);
        assertEquals("Burton McMurtry", created.getNome());
    }
    
    @Test
    void update() {
        Correntista createdMock = createCorrentista("Scott Antonn", "scottanton@gmail.com", "73190252050");
        when(update.execute(1, createdMock)).thenReturn(createdMock);
        Correntista correntista = controller.update(1, createdMock);
        assertEquals("Scott Antonn", correntista.getNome());
    }
    
    @Test
    void remove() {
        doNothing().when(remove).execute(2);
        controller.remove(2);
        verify(remove, times(1)).execute(2);
    }
    
    @Test
    void find() {
        Correntista createdMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(find.execute(3)).thenReturn(createdMock);
        Correntista correntista = controller.findById(3);
        assertEquals("Charles Benson", correntista.getNome());
    }
    
    @Test
    void search() {
        Correntista createdMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(search.execute(PageRequest.of(0, 10), "Charles")).thenReturn(new PageImpl<>(singletonList(createdMock)));
        Page<Correntista> correntistas = controller.search(PageRequest.of(0, 10), "Charles");
        assertEquals(1, correntistas.getContent().size());
        assertEquals("Charles Benson", correntistas.getContent().get(0).getNome());
    }
}
