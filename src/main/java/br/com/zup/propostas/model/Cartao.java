package br.com.zup.propostas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NotBlank String numCartao;
    private @NotBlank String titular;
    private @NotBlank String emitidoEm;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numCartao, @NotBlank String titular, @NotBlank String emitidoEm) {
        this.numCartao = numCartao;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
    }
}
