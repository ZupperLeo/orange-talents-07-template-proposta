package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.AnaliseApiForm;
import br.com.zup.propostas.dto.AnaliseApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/*Nas versões mais recentes do Feign,
 precisamos passar além da url, um nome para nosso cliente.*/
@FeignClient(url = "http://localhost:9999", name = "solicitacao")
public interface AnaliseFinanceiraClient {

    @PostMapping("/api/solicitacao")
    AnaliseApiDTO consulta(@Valid @RequestBody AnaliseApiForm form);

}
