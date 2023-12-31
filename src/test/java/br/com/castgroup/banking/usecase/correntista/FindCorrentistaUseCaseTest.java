package br.com.castgroup.banking.usecase.correntista;

import br.com.castgroup.banking.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindCorrentistaUseCaseTest {
    @InjectMocks
    private FindCorrentistaUseCase service;
    
    @Mock
    private CorrentistaRepository repository;
    
    @Test
    void find() {
        Correntista createdMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        when(repository.findById(3)).thenReturn(Optional.of(createdMock));
        Correntista correntista = service.execute(3);
        assertEquals("Charles Benson", correntista.getNome());
    }
    
    @Test
    void findNotFound() {
        when(repository.findById(5)).thenReturn(Optional.empty());
        NotFoundException exc = assertThrows(NotFoundException.class, () -> service.execute(5));
        assertEquals("Entity 5 not found", exc.getMessage());
    }
}
