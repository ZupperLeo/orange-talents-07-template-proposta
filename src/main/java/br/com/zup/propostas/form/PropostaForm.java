package br.com.zup.propostas.form;

import br.com.zup.propostas.config.validators.CPFCNPJValidator;
import br.com.zup.propostas.model.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaForm {

    @CPFCNPJValidator
    private String cpf;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salario;

    public PropostaForm(String cpf, @NotBlank @Email String email, @NotBlank String nome,
                        @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(this.cpf, this.email, this.nome, this.endereco, this.salario);
    }
}
