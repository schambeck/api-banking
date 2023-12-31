package br.com.castgroup.banking.usecase.movimentacao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoRestEndpoint {
    private static final MovimentacaoMapper mapper = MovimentacaoMapper.INSTANCE;
    private final MovimentacaoController controller;
    
    @GetMapping("{id}")
    public ResponseEntity<MovimentacaoWeb> get(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toWeb(controller.findById(id)));
    }

    @GetMapping("extrato/{contaId}")
    public ResponseEntity<Page<MovimentacaoWeb>> extrato(@PageableDefault Pageable pageable, @PathVariable int contaId) {
        return ResponseEntity.ok(controller.extrato(pageable, contaId).map(mapper::toWeb));
    }

    @GetMapping("extrato/{numeroConta}/{agenciaConta}")
    public ResponseEntity<Page<MovimentacaoWeb>> extrato(@PageableDefault Pageable pageable, @PathVariable int numeroConta, @PathVariable String agenciaConta) {
        return ResponseEntity.ok(controller.extrato(pageable, numeroConta, agenciaConta).map(mapper::toWeb));
    }
}
