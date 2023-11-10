package br.com.castgroup.banking.usecase.saque;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaqueController {
    private final CreateSaqueInteractor createSaque;

    @Transactional
    public Saque create(CreateSaqueRequest request) {
        return createSaque.execute(request.getDadosConta(), request.getData(), request.getValor());
    }
}
