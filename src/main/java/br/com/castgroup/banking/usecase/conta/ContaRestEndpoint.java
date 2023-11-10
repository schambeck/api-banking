package br.com.castgroup.banking.usecase.conta;

import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaRestEndpoint {
    private final ContaMapper mapper;
    private final ContaController controller;

    @PostMapping
    public ResponseEntity<ContaWeb> create(@RequestBody ContaWeb web) {
        Conta created = controller.createContaSaldoZerado(mapper.toEntity(web));
        return ResponseEntity.ok(mapper.toWeb(created));
    }

    @PutMapping("{id}")
    public ResponseEntity<ContaWeb> update(@PathVariable int id, @RequestBody ContaWeb web) {
        Conta updated = controller.update(id, mapper.toEntity(web));
        return ResponseEntity.ok(mapper.toWeb(updated));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable int id) {
        controller.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ContaWeb> findById(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toWeb(controller.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ContaWeb>> search(@PageableDefault Pageable pageable,
                                                 @RequestParam(required = false) @Digits(integer = 12, fraction = 0, message = "Search by Valor must be a valid number with a maximum of 12 integral digits and 0 fractional digits") String text) {
        return ResponseEntity.ok(controller.search(pageable, text).map(mapper::toWeb));
    }
}
