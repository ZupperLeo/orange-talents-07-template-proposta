package br.com.zup.propostas.config.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AplicacaoSaude implements HealthIndicator {

    @Override
    public Health health() {
        Health.Builder status = Health.up();
        Map<String, Object> details = new HashMap<>();
        details.put("Versão", "1.0.0");
        details.put("Descrição", "HealthCheck customizado!");
        details.put("Endereço", "http://localhost:8080/actuator/health");

        return status.withDetails(details).build();
    }

}
