package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.BiometriaForm;
import br.com.zup.propostas.dto.BloqueiaApiForm;
import br.com.zup.propostas.model.Biometria;
import br.com.zup.propostas.model.Bloqueia;
import br.com.zup.propostas.model.Cartao;
import br.com.zup.propostas.model.StatusCartao;
import br.com.zup.propostas.repository.BiometriaRepository;
import br.com.zup.propostas.repository.BloqueiaRepository;
import br.com.zup.propostas.repository.CartaoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository cardRepository;
    @Autowired
    private BiometriaRepository biometriaRepository;
    @Autowired
    private BloqueiaRepository bloqueiaRepository;
    @Autowired
    private CartoesClient client;

    @PostMapping(value = "/{numCartao}/biometria")
    @Transactional
    public ResponseEntity<URI> cadastrar(@PathVariable String numCartao, @RequestBody @Valid BiometriaForm form, UriComponentsBuilder builder) {
        Optional<Cartao> verifica = cardRepository.findByNumCartao(numCartao);

        if(verifica.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = verifica.get();
        Biometria biometria = form.toModel(cartao);
        biometriaRepository.save(biometria);

        URI uri = builder.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.ok().body(uri);
    }

    @PostMapping(value = "/{numCartao}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquear(HttpServletRequest request, @PathVariable String numCartao) {
        Optional<Cartao> verifica = cardRepository.findByNumCartao(numCartao);

        String ipTitular = request.getRemoteHost();
        String userAgent = request.getHeader("User-Agent");

        if(ipTitular == null) {
            ipTitular = request.getRemoteAddr();
        }

        if(verifica.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = verifica.get();

        if(cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.status(422).build();
        }

        try {
            BloqueiaApiForm apiForm = new BloqueiaApiForm("proposta");
            client.bloquear(numCartao, apiForm);
        } catch (FeignException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        Bloqueia bloqueia = new Bloqueia(cartao, ipTitular, userAgent);
        bloqueiaRepository.save(bloqueia);
        cardRepository.save(cartao);

        return ResponseEntity.ok().build();
    }
}
