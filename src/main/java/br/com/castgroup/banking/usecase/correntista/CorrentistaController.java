package br.com.castgroup.banking.usecase.correntista;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorrentistaController {
    private final CreateCorrentistaUseCase create;
    private final UpdateCorrentistaUseCase update;
    private final RemoveCorrentistaUseCase remove;
    private final FindCorrentistaUseCase find;
    private final SearchCorrentistaUseCase search;

    public Correntista create(Correntista entity) {
        return create.execute(entity);
    }

    public Correntista update(int id, Correntista entity) {
        return update.execute(id, entity);
    }

    public void remove(int id) {
        remove.execute(id);
    }

    public Correntista findById(int id) {
        return find.execute(id);
    }

    public Page<Correntista> search(Pageable pageable, String text) {
        return search.execute(pageable, text);
    }
}
