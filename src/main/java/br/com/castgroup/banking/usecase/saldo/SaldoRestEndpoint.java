    package br.com.castgroup.banking.usecase.saldo;
    
    import jakarta.validation.constraints.Digits;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.web.PageableDefault;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("saldos")
@RequiredArgsConstructor
public class SaldoRestEndpoint {
    private static final SaldoMapper mapper = SaldoMapper.INSTANCE;
    private final SaldoController controller;

    @GetMapping("{id}")
    public ResponseEntity<SaldoWeb> findById(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toWeb(controller.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<SaldoWeb>> search(@PageableDefault Pageable pageable,
                                                 @RequestParam(required = false) @Digits(integer = 12, fraction = 0, message = "Search by Valor must be a valid number with a maximum of 12 integral digits and 0 fractional digits") String text) {
        return ResponseEntity.ok(controller.search(pageable, text).map(mapper::toWeb));
    }
}
