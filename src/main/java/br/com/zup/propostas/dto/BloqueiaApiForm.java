package br.com.zup.propostas.dto;

import javax.validation.constraints.NotBlank;

public class BloqueiaApiForm {

    private @NotBlank String sistemaResponsavel;

    public BloqueiaApiForm(@NotBlank String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
