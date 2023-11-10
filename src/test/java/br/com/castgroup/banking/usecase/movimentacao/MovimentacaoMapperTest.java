package br.com.castgroup.banking.usecase.movimentacao;

import br.com.castgroup.banking.usecase.conta.Conta;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.castgroup.banking.usecase.conta.ContaUtil.createConta;
import static br.com.castgroup.banking.usecase.movimentacao.MovimentacaoUtil.createMovimentacao;
import static br.com.castgroup.banking.usecase.movimentacao.TipoMovimentacao.DEBITO;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovimentacaoMapperTest {
    MovimentacaoMapper mapper = Mappers.getMapper(MovimentacaoMapper.class);
    
    @Test
    void toWebNull() {
        MovimentacaoWeb web = mapper.toWeb(null);
        assertNull(web);
    }

    @Test
    void toWebCorrentistaNull() {
        Conta conta = createConta(1, "1111", null);
        Movimentacao entity = createMovimentacao(conta, LocalDate.now(), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        
        MovimentacaoWeb web = mapper.toWeb(entity);
        
        assertNull(web.getConta().getCorrentista());
    }

    @Test
    void toWebCotaNull() {
        Movimentacao entity = createMovimentacao(null, LocalDate.now(), DEBITO, new BigDecimal("123"), new BigDecimal("0"));
        
        MovimentacaoWeb web = mapper.toWeb(entity);
        
        assertNull(web.getConta());
    }
}
