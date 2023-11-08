package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = RemoveCorrentistaUseCase.class)
class RemoveCorrentistaUseCaseTest {
    @Autowired
    private RemoveCorrentistaUseCase service;
    
    @MockBean
    private CorrentistaRepository repository;
    
    @Test
    void remove() {
        doNothing().when(repository).deleteById(2);
        service.execute(2);
        verify(repository, times(1)).deleteById(2);
    }
}
