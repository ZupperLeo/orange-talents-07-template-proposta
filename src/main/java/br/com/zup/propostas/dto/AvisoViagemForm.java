package br.com.zup.propostas.dto;

import br.com.zup.propostas.model.AvisoViagem;
import br.com.zup.propostas.model.Cartao;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemForm {

    private @NotBlank String destinoViagem;
    private @NotNull @Future LocalDate dataTermino;

    @Deprecated
    public AvisoViagemForm() {
    }

    public AvisoViagemForm(@NotBlank String destinoViagem, @NotNull @Future LocalDate dataTermino) {
        this.destinoViagem = destinoViagem;
        this.dataTermino = dataTermino;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public AvisoViagem toModel(@NotNull @Valid Cartao cartao, @NotBlank String ipTitular,
                               @NotBlank String userAgent) {
        return new AvisoViagem(userAgent, ipTitular, cartao, this.destinoViagem, this.dataTermino);
    }
}
