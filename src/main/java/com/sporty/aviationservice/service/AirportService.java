package com.sporty.aviationservice.service;

import com.sporty.aviationservice.client.AviationApiClient;
import com.sporty.aviationservice.controller.airport.AirportResponse;
import com.sporty.aviationservice.exception.AviationApiException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportService {
  private final AviationApiClient aviationApiClient;
  private final MeterRegistry registry;

  @Retryable(
      retryFor = AviationApiException.class,
      maxAttemptsExpression = "${api.aviation.retry-max-attempts}",
      backoff = @Backoff(delayExpression = "${api.aviation.retry-max-delay}"))
  public AirportResponse getAirport(String icao) {
    Timer.Sample sample = Timer.start(registry);
    try {
      var airportsClientResponse = aviationApiClient.getAirportByApt(icao);
      return AirportResponse.mapper(airportsClientResponse.getAirportDetails());
    } finally {
      sample.stop(registry.timer("airport.get", "client", "aviationApi"));
    }
  }
}
