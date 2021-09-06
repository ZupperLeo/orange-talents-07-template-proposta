package br.com.zup.propostas.model;

import br.com.zup.propostas.config.service.PrintEncoded;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NotBlank String fingerprint;
    private @NotNull LocalDateTime dataCadastro = LocalDateTime.now();
    @ManyToOne
    private @NotNull Cartao cartao;

    @Deprecated
    public Biometria(){
    }

    public Biometria(PrintEncoded fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint.getFingerPrint();
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

}
