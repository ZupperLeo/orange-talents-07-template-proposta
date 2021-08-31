package br.com.zup.propostas.dto;

import br.com.zup.propostas.config.validators.CPFCNPJValidator;
import br.com.zup.propostas.model.Proposta;

import javax.validation.constraints.NotBlank;

public class AnaliseApiForm {

    @CPFCNPJValidator(domainClass = Proposta.class, fieldName = "documento")
    private String documento;
    @NotBlank
    private String nome;
    @NotBlank
    private String idProposta;

    public AnaliseApiForm(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
