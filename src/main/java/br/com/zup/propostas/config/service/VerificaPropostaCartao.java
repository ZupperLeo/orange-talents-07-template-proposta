package br.com.zup.propostas.config.service;

import br.com.zup.propostas.controller.CartoesClient;
import br.com.zup.propostas.dto.CartaoApiDTO;
import br.com.zup.propostas.model.Cartao;
import br.com.zup.propostas.model.Proposta;
import br.com.zup.propostas.repository.CartaoRepository;
import br.com.zup.propostas.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Verifica a cada 1 minuto se ha propostas elegiveis sem cartao atrelado
 */
@Component
public class VerificaPropostaCartao {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartoesClient client;

    @Scheduled(fixedDelay = 60000)
    void atrelaCartao() {
        List<Proposta> propostas = propostaRepository.findByStatusAndCartaoNull();

        for(Proposta proposta : propostas) {
            CartaoApiDTO api = client.buscaCartao(proposta.getId().toString());
            Cartao cartao = api.toModel();
            cartaoRepository.save(cartao);
            proposta.setCartao(cartao);
            propostaRepository.save(proposta);
        }
    }
}
