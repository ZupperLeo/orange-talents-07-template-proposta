package br.com.zup.propostas.dto;

import br.com.zup.propostas.model.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraApiForm {

    private @Email @NotBlank String email;
    private @NotNull TipoCarteira carteira;

    public CarteiraApiForm(@Email @NotBlank String email, @NotNull TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCarteira(TipoCarteira carteira) {
        this.carteira = carteira;
    }

}
