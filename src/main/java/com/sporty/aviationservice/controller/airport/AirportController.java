package com.sporty.aviationservice.controller.airport;

import com.sporty.aviationservice.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airports")
public class AirportController {
  private final AirportService service;

  @GetMapping("/{icao}")
  public AirportResponse byIcao(@PathVariable String icao) {
    return service.getAirport(icao);
  }
}
