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
class UpdateCorrentistaUseCaseTest {
    @InjectMocks
    private UpdateCorrentistaUseCase service;
    
    @Mock
    private CorrentistaRepository repository;
    
    @Test
    void update() {
        Correntista createdMock = createCorrentista(1, "Scott Antonn", "scottanton@gmail.com", "73190252050");
        when(repository.findById(1)).thenReturn(Optional.of(createdMock));
        when(repository.save(createdMock)).thenReturn(createdMock);
        Correntista correntista = service.execute(1, createdMock);
        assertEquals("Scott Antonn", correntista.getNome());
    }
    
    @Test
    void updateNotFound() {
        Correntista createdMock = createCorrentista(5, "Robert V Davis", "robertdavis@gmail.com", "35814875003");
        when(repository.findById(5)).thenReturn(Optional.empty());
        when(repository.save(createdMock)).thenReturn(createdMock);
        NotFoundException exc = assertThrows(NotFoundException.class, () -> service.execute(5, createdMock));
        assertEquals("Entity 5 not found", exc.getMessage());
    }
}
