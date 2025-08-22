package com.sporty.aviationservice.controller.airport;

import com.sporty.aviationservice.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airports")
@Tag(name = "User", description = "User endpoints. Roles: **ROLE_USER**, **ROLE_ADMIN**")
public class AirportController {
  private final AirportService service;

  @GetMapping("/{icao}")
  @Operation(
      summary = "Get an airport by ICAO code",
      description = "Requires **ROLE_USER** or **ROLE_ADMIN**.",
      extensions =
          @io.swagger.v3.oas.annotations.extensions.Extension(
              name = "x-roles",
              properties = {
                @io.swagger.v3.oas.annotations.extensions.ExtensionProperty(
                    name = "allowed",
                    value = "[\"ROLE_USER\",\"ROLE_ADMIN\"]")
              }))
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public AirportResponse byIcao(@AuthenticationPrincipal Jwt jwt, @PathVariable String icao) {
    return service.getAirport(icao);
  }
}
