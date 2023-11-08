package br.com.castgroup.banking.usecase.correntista;

public final class CorrentistaUtil {
    private CorrentistaUtil() {
    }
    
    public static Correntista createCorrentista(String nome, String email, String cpf) {
        return createCorrentista(null, nome, email, cpf);
    }
    
    public static Correntista createCorrentista(Integer id, String nome, String email, String cpf) {
        return Correntista.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .cpf(cpf)
                .build();
    }
}
