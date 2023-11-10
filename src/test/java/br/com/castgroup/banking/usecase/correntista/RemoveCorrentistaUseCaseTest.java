package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RemoveCorrentistaUseCaseTest {
    @InjectMocks
    private RemoveCorrentistaUseCase service;
    
    @Mock
    private CorrentistaRepository repository;
    
    @Test
    void remove() {
        doNothing().when(repository).deleteById(2);
        service.execute(2);
        verify(repository, times(1)).deleteById(2);
    }
}
