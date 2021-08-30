package br.com.zup.propostas.controller;

import br.com.zup.propostas.form.PropostaForm;
import br.com.zup.propostas.model.Proposta;
import br.com.zup.propostas.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<URI> criar(@Valid @RequestBody PropostaForm form, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> verifica = repository.findByDocumento(form.getDocumento());

        if(verifica.isPresent()) {
            return ResponseEntity.status(422).build();
        }

        Proposta proposta = form.toModel();
        repository.save(proposta);

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.status(201).body(uri);
    }
}
