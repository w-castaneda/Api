package com.rest.service.Api.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter customEndpointCounter(MeterRegistry meterRegistry) {
        return Counter.builder("custom_endpoint_requests")
                .description("Cuenta el número de solicitudes al endpoint /api/v1/{id}")
                .tags("method", "GET", "endpoint", "/api/v1/custom")
                .register(meterRegistry);
    }
}

