package br.com.zup.propostas.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @Email @NotBlank String email;
    @Enumerated(EnumType.STRING)
    private @NotNull TipoCarteira tipoCarteira;
    @Enumerated(EnumType.STRING)
    private ResultadoCarteira resultado;
    @ManyToOne
    private @NotNull Cartao cartao;

    public Carteira(@Email @NotBlank String email, @NotNull TipoCarteira tipoCarteira, @NotNull Cartao cartao) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira = (Carteira) o;
        return getTipoCarteira() == carteira.getTipoCarteira();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTipoCarteira());
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }
}
