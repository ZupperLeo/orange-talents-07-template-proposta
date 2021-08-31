package br.com.zup.propostas.model;

public enum ResultadoSolicitacao {

    COM_RESTRICAO{
        StatusProposta getStatus() {
            return StatusProposta.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        StatusProposta getStatus() {
            return StatusProposta.ELEGIVEL;
        }
    };

    abstract StatusProposta getStatus();
}
