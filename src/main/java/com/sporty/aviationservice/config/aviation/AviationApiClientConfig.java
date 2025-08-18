package com.sporty.aviationservice.config.aviation;

import com.sporty.aviationservice.domain.Error;
import com.sporty.aviationservice.exception.AviationApiException;
import feign.Logger;
import feign.Request;
import feign.codec.ErrorDecoder;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Slf4j
@Configuration
public class AviationApiClientConfig {

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public Request.Options feignOptions() {
    return new Request.Options(5, TimeUnit.SECONDS, 15, TimeUnit.SECONDS, true);
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return (methodKey, response) -> {
      log.error("Error at Aviation API! Reason: {}", response.reason());
      return new AviationApiException(
          Error.AVIATION_API_ERROR, HttpStatus.valueOf(response.status()));
    };
  }
}
