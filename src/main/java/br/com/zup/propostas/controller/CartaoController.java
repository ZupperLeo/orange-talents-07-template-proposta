package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.BiometriaForm;
import br.com.zup.propostas.model.Biometria;
import br.com.zup.propostas.model.Cartao;
import br.com.zup.propostas.repository.BiometriaRepository;
import br.com.zup.propostas.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    private BiometriaRepository bioRepository;

    @PostMapping(value = "/{numCartao}/biometria")
    @Transactional
    public ResponseEntity<URI> cadastrar(@PathVariable String numCartao, @RequestBody @Valid BiometriaForm form, UriComponentsBuilder builder) {
        Optional<Cartao> verifica = cardRepository.findByNumCartao(numCartao);

        if(verifica.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = verifica.get();
        Biometria biometria = form.toModel(cartao);
        bioRepository.save(biometria);

        URI uri = builder.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.ok().body(uri);
    }
}
