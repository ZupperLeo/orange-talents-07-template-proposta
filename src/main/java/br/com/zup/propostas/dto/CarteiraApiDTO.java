package br.com.zup.propostas.dto;

public class CarteiraApiDTO {

    private String resultado;
    private String id;

    public CarteiraApiDTO(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
