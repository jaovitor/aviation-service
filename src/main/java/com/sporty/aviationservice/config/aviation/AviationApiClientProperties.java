package com.sporty.aviationservice.config.aviation;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.aviation")
public record AviationApiClientProperties(
    String baseUrl, Integer retryMaxAttempts, Integer retryMaxDelay) {}
