package br.com.zup.propostas.repository;

import br.com.zup.propostas.model.Carteira;
import br.com.zup.propostas.model.ResultadoCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    Set<Carteira> findByResultadoAndCartaoNumCartao(ResultadoCarteira resultadoCarteira, String numCartao);
}
