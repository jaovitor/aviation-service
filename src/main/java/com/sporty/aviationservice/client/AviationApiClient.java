package com.sporty.aviationservice.client;

import com.sporty.aviationservice.client.response.AirportsClientResponse;
import com.sporty.aviationservice.config.aviation.AviationApiClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "aviationApi",
    url = "${api.aviation.base-url}",
    configuration = AviationApiClientConfig.class)
public interface AviationApiClient {

  @GetMapping(value = "/v1/airports", consumes = MediaType.APPLICATION_JSON_VALUE)
  AirportsClientResponse getAirportByApt(@RequestParam("apt") String apt);
}
