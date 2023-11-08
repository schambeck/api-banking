package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ContaController.class)
class ContaControllerTest {
    @Autowired
    private ContaController controller;
    
    @MockBean
    private CreateContaUseCase create;
    
    @MockBean
    private UpdateContaUseCase update;
    
    @MockBean
    private RemoveContaUseCase remove;
    
    @MockBean
    private FindContaUseCase find;
    
    @MockBean
    private SearchContaUseCase search;
    
    @Test
    void create() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        Conta createdMock = createConta(4, 4, "4444", correntistaMock);
        when(create.execute(toCreate)).thenReturn(createdMock);
        Conta created = controller.create(toCreate);
        assertEquals(4, created.getNumero());
    }
    
    @Test
    void update() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toUpdate = createConta(1, 1, "1112", correntistaMock);
        when(update.execute(1, toUpdate)).thenReturn(toUpdate);
        Conta conta = controller.update(1, toUpdate);
        assertEquals(1, conta.getNumero());
    }
    
    @Test
    void remove() {
        doNothing().when(remove).execute(2);
        controller.remove(2);
        verify(remove, times(1)).execute(2);
    }
    
    @Test
    void find() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta createdMock = createConta( 3, "3333", correntistaMock);
        when(find.execute(3)).thenReturn(createdMock);
        Conta conta = controller.findById(3);
        assertEquals(3, conta.getNumero());
    }
    
    @Test
    void search() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta createdMock = createConta( 3, "3333", correntistaMock);
        when(search.execute(PageRequest.of(0, 10), "3")).thenReturn(new PageImpl<>(singletonList(createdMock)));
        Page<Conta> contas = controller.search(PageRequest.of(0, 10), "3");
        assertEquals(1, contas.getContent().size());
        assertEquals(3, contas.getContent().get(0).getNumero());
    }
}
