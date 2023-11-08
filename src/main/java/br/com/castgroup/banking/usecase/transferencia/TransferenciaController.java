package br.com.castgroup.banking.usecase.transferencia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferenciaController {
    private final CreateTransferenciaInteractor createTransferencia;

    @Transactional
    public Transferencia create(CreateTransferenciaRequest request) {
        return createTransferencia.execute(request.getDadosContaOrigem(), request.getDadosContaDestino(), request.getValor());
    }
}
