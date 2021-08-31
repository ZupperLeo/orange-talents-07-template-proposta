package br.com.zup.propostas.model;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private StatusProposta status;

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
        this.status = StatusProposta.NAO_ELEGIVEL;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void status(ResultadoSolicitacao resultado) {
        this.status = resultado.getStatus();
    }
}
