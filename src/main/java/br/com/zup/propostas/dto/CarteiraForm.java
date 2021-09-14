package br.com.zup.propostas.dto;

import br.com.zup.propostas.model.Cartao;
import br.com.zup.propostas.model.Carteira;
import br.com.zup.propostas.model.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraForm {

    private @Email @NotBlank String email;
    private @NotNull TipoCarteira carteira;

    @Deprecated
    public CarteiraForm() {
    }

    public CarteiraForm(@Email @NotBlank String email, @NotNull TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(this.email, this.carteira, cartao);
    }
}
