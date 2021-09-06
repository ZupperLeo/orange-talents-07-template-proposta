package br.com.zup.propostas.dto;

import br.com.zup.propostas.config.service.PrintEncoded;
import br.com.zup.propostas.model.Biometria;
import br.com.zup.propostas.model.Cartao;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BiometriaForm {

    private @NotBlank String fingerprint;

    public BiometriaForm(@JsonProperty(value = "fingerPrint") String fingerPrint) {
        this.fingerprint = fingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(new PrintEncoded(fingerprint), cartao);
    }
}
