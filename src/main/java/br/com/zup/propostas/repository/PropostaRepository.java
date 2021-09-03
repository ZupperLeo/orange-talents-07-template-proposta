package br.com.zup.propostas.repository;

import br.com.zup.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    @Query(
            "SELECT p FROM Proposta AS p WHERE p.status = 'ELEGIVEL' AND p.cartao = NULL"
    )
    List<Proposta> findByStatusAndCartaoNull();
}
