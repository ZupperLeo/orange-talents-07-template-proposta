package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.CartaoApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8888", name = "cartoes")
public interface CartoesClient {

    @GetMapping("/api/cartoes")
    CartaoApiDTO buscaCartao(@RequestParam String idProposta);
}
