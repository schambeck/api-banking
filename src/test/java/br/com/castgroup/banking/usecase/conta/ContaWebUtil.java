package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.CorrentistaWeb;

public final class ContaWebUtil {
    private ContaWebUtil() {
    }
    
    public static ContaWeb createContaWeb(Integer numero, String agencia, CorrentistaWeb correntista) {
        return createContaWeb(null, numero, agencia, correntista);
    }

    public static ContaWeb createContaWeb(Integer id, Integer numero, String agencia, CorrentistaWeb correntista) {
        return ContaWeb.builder()
                .id(id)
                .numero(numero)
                .agencia(agencia)
                .correntista(correntista)
                .build();
    }
}
