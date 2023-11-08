package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaldoRepository extends JpaRepository<Saldo, Integer> {
    Optional<Saldo> findByConta(Conta conta);
    
    Optional<Saldo> findByConta_Id(int contaId);
}
