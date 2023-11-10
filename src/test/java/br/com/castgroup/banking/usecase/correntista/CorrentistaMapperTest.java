package br.com.castgroup.banking.usecase.correntista;

import org.junit.jupiter.api.Test;

import static br.com.castgroup.banking.usecase.correntista.CorrentistaUtil.createCorrentista;
import static br.com.castgroup.banking.usecase.correntista.CorrentistaWebUtil.createCorrentistaWeb;
import static org.junit.jupiter.api.Assertions.*;

class CorrentistaMapperTest {
    @Test
    void toWeb() {
        Correntista correntista = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        CorrentistaWeb web = CorrentistaMapper.INSTANCE.toWeb(correntista);

        assertEquals("Charles Benson", web.getNome());
    }

    @Test
    void toWebNull() {
        CorrentistaWeb web = CorrentistaMapper.INSTANCE.toWeb(null);
        assertNull(web);
    }

    @Test
    void toEntity() {
        CorrentistaWeb correntista = createCorrentistaWeb(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        Correntista entity = CorrentistaMapper.INSTANCE.toEntity(correntista);
        
        assertEquals("Charles Benson", entity.getNome());
    }

    @Test
    void toEntityNull() {
        Correntista entity = CorrentistaMapper.INSTANCE.toEntity(null);
        assertNull(entity);
    }
    
    @Test
    void copyNull() {
        Correntista target = createCorrentista(3, "Charles Benson", "charlesbenson@gmail.com", "07095797056");
        
        CorrentistaMapper.INSTANCE.copy(null, target);
        
        assertEquals("Charles Benson", target.getNome());
    }
}
