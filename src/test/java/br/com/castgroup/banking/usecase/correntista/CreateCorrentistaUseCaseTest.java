package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateCorrentistaUseCaseTest {
    @InjectMocks
    private CreateCorrentistaUseCase service;
    
    @Mock
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
