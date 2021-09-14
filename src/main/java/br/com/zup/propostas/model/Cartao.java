package br.com.zup.propostas.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NotBlank String numCartao;
    private @NotBlank String titular;
    private @NotBlank String emitidoEm;
    @Enumerated(EnumType.STRING)
    private @NotNull StatusCartao status = StatusCartao.ATIVO;
    @OneToMany(mappedBy = "cartao")/*, cascade = CascadeType.MERGE)*/
    private Set<Carteira> carteiras;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numCartao, @NotBlank String titular, @NotBlank String emitidoEm) {
        this.numCartao = numCartao;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public StatusCartao getStatus() {
        return status;
    }

    /**
     * Metodo responsavel por realizar o bloqueio do cartao
     * */
    protected void bloquear() {
        this.status = StatusCartao.BLOQUEADO;
    }

    public boolean adicionarCarteira(Carteira carteira) {
        return this.carteiras.add(carteira);
    }
}
