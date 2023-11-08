package br.com.castgroup.banking.usecase.conta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = RemoveContaUseCase.class)
class RemoveContaUseCaseTest {
    @Autowired
    private RemoveContaUseCase service;
    
    @MockBean
    private ContaRepository repository;
    
    @Test
    void remove() {
        doNothing().when(repository).deleteById(2);
        service.execute(2);
        verify(repository, times(1)).deleteById(2);
    }
}
