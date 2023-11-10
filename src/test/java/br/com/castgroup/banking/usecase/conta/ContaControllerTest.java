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

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ContaControllerTest {
    @InjectMocks
    private ContaController controller;
    
    @Mock
    private CreateContaSaldoZeradoInteractor create;
    
    @Mock
    private UpdateContaUseCase update;
    
    @Mock
    private RemoveContaUseCase remove;
    
    @Mock
    private FindContaUseCase find;
    
    @Mock
    private SearchContaUseCase search;
    
    @Mock
    private FindContaByNumeroAndAgenciaUseCase findContaByNumeroAndAgencia;
    
    @Test
    void create() {
        Correntista correntistaMock = createCorrentista(1, "Scott Anton", "scottanton@gmail.com", "73190252050");
        Conta toCreate = createConta(4, "4444", correntistaMock);
        Conta createdMock = createConta(4, 4, "4444", correntistaMock);
        when(create.execute(toCreate)).thenReturn(createdMock);
        Conta created = controller.createContaSaldoZerado(toCreate);
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
    
    @Test
    void findByNumeroAndAgencia() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta createdMock = createConta( 3, "3333", correntistaMock);
        when(findContaByNumeroAndAgencia.execute(3, "3333")).thenReturn(createdMock);
        Conta conta = controller.findByNumeroAndAgencia(3, "3333");
        assertEquals(3, conta.getNumero());
    }
}
