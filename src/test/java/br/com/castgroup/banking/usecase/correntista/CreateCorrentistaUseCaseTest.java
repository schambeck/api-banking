package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateCorrentistaUseCase.class)
class CreateCorrentistaUseCaseTest {
    @Autowired
    private CreateCorrentistaUseCase service;
    
    @MockBean
    private CorrentistaRepository repository;
    
    @Test
    void create() {
        Correntista toCreate = createCorrentista("Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        Correntista createdMock = createCorrentista(3, "Burton McMurtry", "burtonmcmurtry@gmail.com", "85182111070");
        when(repository.save(toCreate)).thenReturn(createdMock);
        Correntista created = service.execute(toCreate);
        assertEquals("Burton McMurtry", created.getNome());
    }
}
