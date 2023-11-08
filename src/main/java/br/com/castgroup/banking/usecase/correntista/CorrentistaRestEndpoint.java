package br.com.castgroup.banking.usecase.correntista;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("correntistas")
@RequiredArgsConstructor
public class CorrentistaRestEndpoint {
    private static final CorrentistaMapper mapper = CorrentistaMapper.INSTANCE;
    private final CorrentistaController controller;

    @PostMapping
    public ResponseEntity<CorrentistaWeb> create(@RequestBody CorrentistaWeb web) {
        Correntista created = controller.create(mapper.toEntity(web));
        return ResponseEntity.ok(mapper.toWeb(created));
    }

    @PutMapping("{id}")
    public ResponseEntity<CorrentistaWeb> update(@PathVariable int id, @RequestBody CorrentistaWeb web) {
        Correntista updated = controller.update(id, mapper.toEntity(web));
        return ResponseEntity.ok(mapper.toWeb(updated));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable int id) {
        controller.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CorrentistaWeb> get(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toWeb(controller.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<CorrentistaWeb>> search(@PageableDefault Pageable pageable,
                                                       @RequestParam(required = false) String text) {
        return ResponseEntity.ok(controller.search(pageable, text).map(mapper::toWeb));
    }
}
