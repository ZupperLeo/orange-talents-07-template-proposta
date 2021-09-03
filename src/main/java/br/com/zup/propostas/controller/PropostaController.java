package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.AnaliseApiDTO;
import br.com.zup.propostas.dto.AnaliseApiForm;
import br.com.zup.propostas.dto.PropostaForm;
import br.com.zup.propostas.model.Proposta;
import br.com.zup.propostas.repository.PropostaRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
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

    private PropostaRepository repository;
    private final Tracer tracer;
    @Autowired
    private AnaliseFinanceiraClient analise;

    public PropostaController(PropostaRepository repository, Tracer tracer) {
        this.repository = repository;
        this.tracer = tracer;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<URI> criar(@Valid @RequestBody PropostaForm form, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> verifica = repository.findByDocumento(form.getDocumento());
        Span span = tracer.activeSpan();
        String userEmail = span.getBaggageItem("user.email");
        span.setTag("user.email", "teste@teste.com");

        if(verifica.isPresent()) {
            return ResponseEntity.status(422).build();
        }

        Proposta proposta = form.toModel();
        repository.save(proposta);

        AnaliseApiForm request = new AnaliseApiForm(proposta);
        AnaliseApiDTO response = analise.consulta(request);
        proposta.status(response.getResultadoSolicitacao());

        repository.save(proposta);

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.status(201).body(uri);
    }
}
