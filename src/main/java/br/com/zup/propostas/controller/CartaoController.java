package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.*;
import br.com.zup.propostas.model.*;
import br.com.zup.propostas.repository.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

/*NOTA:
 * estou incomodado com o tamanho que esse controller estah ficando, pretendo refatora-lo futuramente
 * se eu irei apenas utilizar o metodo save da interface, nao seria melhor utilizar o EntityManager?*/

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
    private AvisoViagemRepository avisoViagemRepository;
    @Autowired
    private CarteiraRepository carteiraRepository;
    @Autowired
    private CartoesClient client;

    @PostMapping(value = "/{numCartao}/biometria")
    @Transactional
    public ResponseEntity<URI> cadastrar(@PathVariable String numCartao, @RequestBody @Valid BiometriaForm form, UriComponentsBuilder builder) {
        Optional<Cartao> buscaCartao = cardRepository.findByNumCartao(numCartao);

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = buscaCartao.get();
        Biometria biometria = form.toModel(cartao);
        biometriaRepository.save(biometria);

        URI uri = builder.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.ok().body(uri);
    }

    @PostMapping(value = "/{numCartao}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquear(HttpServletRequest request, @PathVariable String numCartao) {
        Optional<Cartao> buscaCartao = cardRepository.findByNumCartao(numCartao);

        String ipTitular = request.getRemoteHost();
        String userAgent = request.getHeader("User-Agent");

        if (ipTitular == null) {
            ipTitular = request.getRemoteAddr();
        }

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = buscaCartao.get();

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
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

    @PostMapping("/{numCartao}/aviso-viagem")
    @Transactional
    public ResponseEntity<?> avisaViagem(HttpServletRequest request, @PathVariable String numCartao,
                                         @RequestBody @Valid AvisoViagemForm form) {
        Optional<Cartao> buscaCartao = cardRepository.findByNumCartao(numCartao);

        String ipTitular = request.getRemoteHost();
        String userAgent = request.getHeader("User-Agent");

        if (ipTitular == null) {
            ipTitular = request.getRemoteAddr();
        }

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Cartao cartao = buscaCartao.get();

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.status(400).build();
        }

        try {
            client.avisar(numCartao, form);
        } catch (FeignException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        AvisoViagem avisoViagem = form.toModel(cartao, ipTitular, userAgent);

        avisoViagemRepository.save(avisoViagem);
        cardRepository.save(cartao);

        return ResponseEntity.status(200).build();
    }

    @PostMapping("/{numCartao}/associar")
    @Transactional
    public ResponseEntity<?> associarCarteira(@PathVariable String numCartao, @RequestBody @Valid CarteiraForm form,
                                              UriComponentsBuilder componentsBuilder) {
        Optional<Cartao> buscaCartao = cardRepository.findByNumCartao(numCartao);

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Cartao cartao = buscaCartao.get();
        Set<Carteira> carteiraList = carteiraRepository
                .findByResultadoAndCartaoNumCartao(ResultadoCarteira.ASSOCIADA, numCartao);

        for (Carteira carteiraEncontrada : carteiraList) {
            if (carteiraEncontrada.getTipoCarteira() == form.getCarteira()) {
                return ResponseEntity.status(422).build();
            }
        }

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.status(400).build();
        }

        Carteira carteira = form.toModel(cartao);

        cardRepository.save(cartao);
        carteiraRepository.save(carteira);

        try {
            CarteiraApiForm apiForm = new CarteiraApiForm(form.getEmail(), form.getCarteira());
            client.associar(numCartao, form);
        } catch (FeignException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carteira.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}







