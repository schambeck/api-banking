package br.com.castgroup.banking.usecase.correntista;

public final class CorrentistaWebUtil {
    private CorrentistaWebUtil() {
    }
    
    public static CorrentistaWeb createCorrentistaWeb(String nome, String email, String cpf) {
        return createCorrentistaWeb(null, nome, email, cpf);
    }
    
    public static CorrentistaWeb createCorrentistaWeb(Integer id, String nome, String email, String cpf) {
        return CorrentistaWeb.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .cpf(cpf)
                .build();
    }
}
