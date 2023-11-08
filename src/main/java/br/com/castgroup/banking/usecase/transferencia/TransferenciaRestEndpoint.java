package br.com.castgroup.banking.usecase.transferencia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transferencias")
@RequiredArgsConstructor
public class TransferenciaRestEndpoint {
    private static final TransferenciaMapper mapper = TransferenciaMapper.INSTANCE;
    private final TransferenciaController service;

    @PostMapping
    public ResponseEntity<TransferenciaWeb> create(@RequestBody CreateTransferenciaRequest request) {
        Transferencia created = service.create(request);
        return ResponseEntity.ok(mapper.toWeb(created));
    }
}
