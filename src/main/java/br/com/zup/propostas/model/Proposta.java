package br.com.zup.propostas.model;

import br.com.zup.propostas.config.security.Jasypt;

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
    private @NotBlank String documentoHash;
    private @Email @NotBlank String email;
    private @NotBlank String nome;
    private @NotBlank String endereco;
    private @NotNull @Positive BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private StatusProposta status;
    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @Email @NotBlank String email, @NotBlank String nome,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = criptografa(documento);
        this.documentoHash = hash(documento);
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

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public void status(ResultadoSolicitacao resultado) {
        this.status = resultado.getStatus();
    }

    private String hash(String documento) {
        return new Jasypt().hash(documento);
    }

    private String criptografa(String documento) {
        return new Jasypt().criptografar(documento);
    }

    private String descriptografar(String documento) {
        return new Jasypt().descriptografar(documento);
    }
}
