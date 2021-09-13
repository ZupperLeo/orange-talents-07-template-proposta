package br.com.zup.propostas.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NotBlank String ipTitular;
    private @NotBlank String userAgent;
    private @NotNull LocalDateTime avisadoEm = LocalDateTime.now();
    private @NotBlank String destinoViagem;
    private @NotNull @Future LocalDate dataTermino;
    @OneToOne
    private @NotNull Cartao cartao;

    public AvisoViagem(@NotBlank String ipTitular, @NotBlank String userAgent, @NotNull @Valid Cartao cartao,
                       @NotBlank String destinoViagem, @NotNull LocalDate dataTermino) {
        this.ipTitular = ipTitular;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.destinoViagem = destinoViagem;
        this.dataTermino = dataTermino;
    }


}
