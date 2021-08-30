package br.com.zup.propostas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NotBlank String documento;
    private @Email @NotBlank String email;
    private @NotBlank String nome;
    private @NotBlank String endereco;
    private @NotNull @Positive BigDecimal salario;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @Email @NotBlank String email, @NotBlank String nome,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }
}
