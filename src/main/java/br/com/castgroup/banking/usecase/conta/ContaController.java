package br.com.castgroup.banking.usecase.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaController {
    private final CreateContaSaldoZeradoInteractor createContaSaldoZerado;
    private final UpdateContaUseCase update;
    private final RemoveContaUseCase remove;
    private final FindContaUseCase find;
    private final SearchContaUseCase search;
    private final FindContaByNumeroAndAgenciaUseCase findByNumeroAndAgencia;

    public Conta createContaSaldoZerado(Conta entity) {
        return createContaSaldoZerado.execute(entity);
    }

    public Conta update(int id, Conta entity) {
        return update.execute(id, entity);
    }

    public void remove(int id) {
        remove.execute(id);
    }

    public Conta findById(int id) {
        return find.execute(id);
    }
    
    public Page<Conta> search(Pageable pageable, String text) {
        return search.execute(pageable, text);
    }
    
    public Conta findByNumeroAndAgencia(int numero, String agencia) {
        return findByNumeroAndAgencia.execute(numero, agencia);
    }
}
