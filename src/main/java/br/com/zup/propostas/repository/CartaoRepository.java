package br.com.zup.propostas.repository;

import br.com.zup.propostas.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Optional<Cartao> findByNumCartao(String numCartao);
}
