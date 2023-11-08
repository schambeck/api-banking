package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrentistaMapperTest {
    @Test
    void toWebNull() {
        CorrentistaWeb web = CorrentistaMapper.INSTANCE.toWeb(null);
        assertNull(web);
    }
    @Test
    void toEntityNull() {
        Correntista entity = CorrentistaMapper.INSTANCE.toEntity(null);
        assertNull(entity);
    }
}
