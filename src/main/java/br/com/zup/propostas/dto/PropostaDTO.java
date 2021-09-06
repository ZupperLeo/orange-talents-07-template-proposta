package br.com.zup.propostas.dto;

import br.com.zup.propostas.model.Proposta;
import br.com.zup.propostas.model.StatusProposta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PropostaDTO {

    private String documento;
    private String nome;
    private StatusProposta status;

    public PropostaDTO(@Valid @NotNull Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.status = proposta.getStatus();
    }

    @Override
    public String toString() {
        return "documento= " + documento + "\n" +
                "nome= " + nome + "\n" +
                "status= " + status;
    }
}
