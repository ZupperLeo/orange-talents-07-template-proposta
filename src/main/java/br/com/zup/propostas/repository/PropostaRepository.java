package br.com.zup.propostas.repository;

import br.com.zup.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

}
