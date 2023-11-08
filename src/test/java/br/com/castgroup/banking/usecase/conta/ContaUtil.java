package br.com.castgroup.banking.usecase.conta;

import br.com.castgroup.banking.usecase.correntista.Correntista;

public final class ContaUtil {
    private ContaUtil() {
    }
    
    public static Conta createConta(Integer numero, String agencia, Correntista correntista) {
        return createConta(null, numero, agencia, correntista);
    }
    
    public static Conta createConta(Integer id, Integer numero, String agencia, Correntista correntista) {
        return Conta.builder()
                .id(id)
                .numero(numero)
                .agencia(agencia)
                .correntista(correntista)
                .build();
    }
}
