package br.com.zup.propostas.dto;

import br.com.zup.propostas.model.Cartao;

public class CartaoApiDTO {

    private String id;
    private String titular;
    private String emitidoEm;

    public CartaoApiDTO(String id, String titular, String emitidoEm) {
        this.id = id;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public String getEmitidoEm() {
        return emitidoEm;
    }

    public Cartao toModel() {
        return new Cartao(id, titular, emitidoEm);
    }
}
