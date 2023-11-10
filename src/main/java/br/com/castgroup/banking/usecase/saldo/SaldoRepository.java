package br.com.castgroup.banking.usecase.saldo;

import br.com.castgroup.banking.usecase.conta.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface SaldoRepository extends JpaRepository<Saldo, Integer> {
//    @NonNull
//    @Override
//    @Lock(PESSIMISTIC_WRITE)
//    Optional<Saldo> findById(@NonNull Integer id);

//    @Lock(PESSIMISTIC_WRITE)
//    @Query("SELECT s FROM Saldo s WHERE s.conta = :conta")
//    Optional<Saldo> findByContaForUpdate(Conta conta);
    
    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Saldo s WHERE s.id = :id")
    Optional<Saldo> lockById(Integer id);
    
//    Optional<Saldo> lockById(Integer id, LockModeType lockMode);
    
    Optional<Saldo> findByConta(Conta conta);
    
    Optional<Saldo> findByConta_Id(int contaId);
    
    Page<Saldo> findAllByOrderById(Pageable pageable);
    
    Page<Saldo> findAllByConta_NumeroOrderByIdAsc(Pageable pageable, Integer numero);
}
