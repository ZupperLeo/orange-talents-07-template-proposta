package br.com.zup.propostas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueia {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private @NotNull Cartao cartao;
    private @NotBlank String ipTitular;
    private @NotBlank String userAgent;
    private @NotNull LocalDateTime momentoDoBloqueio = LocalDateTime.now();

    @Deprecated
    public Bloqueia() {
    }

    public Bloqueia(@NotNull Cartao cartao, @NotBlank String ipTitular, @NotBlank String userAgent) {
        this.cartao = cartao;
        this.ipTitular = ipTitular;
        this.userAgent = userAgent;
        bloqueiaCartao(this.cartao);
    }


    private static void bloqueiaCartao(Cartao cartao) {
        cartao.bloquear();
    }
}
