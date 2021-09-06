package br.com.zup.propostas.config.service;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.constraints.NotBlank;

public class PrintEncoded {

    private String fingerPrint;

    public PrintEncoded(@NotBlank String base64) {
        this.fingerPrint = Base64.encodeBase64String(base64.getBytes());

        if(!Base64.isBase64(this.fingerPrint)) {
          throw new IllegalArgumentException("Base64: dado invalido!");
        }

    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
