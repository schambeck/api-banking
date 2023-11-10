package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindContaByNumeroAndAgenciaUseCaseTest {
    @InjectMocks
    FindContaByNumeroAndAgenciaUseCase findContaByNumeroAndAgencia;
    
    @Mock
    ContaRepository repository;
    
    @Test
    void execute() {
        Correntista correntistaMock = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Conta contaMock = createConta(3, "3333", correntistaMock);
        when(repository.findByNumeroAndAgencia(3, "3333")).thenReturn(Optional.of(contaMock));
        
        Conta conta = findContaByNumeroAndAgencia.execute(3, "3333");
        
        assertEquals(3, conta.getNumero());
    }
}
