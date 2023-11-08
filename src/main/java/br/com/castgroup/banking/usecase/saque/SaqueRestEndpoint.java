package br.com.castgroup.banking.usecase.saque;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("saques")
@RequiredArgsConstructor
public class SaqueRestEndpoint {
    private static final SaqueMapper mapper = SaqueMapper.INSTANCE;
    private final SaqueController service;

    @PostMapping
    public ResponseEntity<SaqueWeb> create(@RequestBody CreateSaqueRequest request) {
        Saque created = service.create(request);
        return ResponseEntity.ok(mapper.toWeb(created));
    }
}
