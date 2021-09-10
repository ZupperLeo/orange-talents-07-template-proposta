package br.com.zup.propostas.controller;

import br.com.zup.propostas.dto.BloqueiaApiDTO;
import br.com.zup.propostas.dto.BloqueiaApiForm;
import br.com.zup.propostas.dto.CartaoApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:8888", name = "cartoes")
public interface CartoesClient {

    @GetMapping("/api/cartoes")
    CartaoApiDTO buscaCartao(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueiaApiDTO bloquear(@PathVariable String id, @RequestBody BloqueiaApiForm form);

}
