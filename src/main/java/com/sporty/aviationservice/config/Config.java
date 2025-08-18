package com.sporty.aviationservice.config;

import com.sporty.aviationservice.config.aviation.AviationApiClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@EnableConfigurationProperties(AviationApiClientProperties.class)
class Config {}
