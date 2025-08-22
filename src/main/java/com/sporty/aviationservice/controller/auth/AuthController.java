package com.sporty.aviationservice.controller.auth;

import com.sporty.aviationservice.config.security.JwtKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication and token endpoints (public).")
public class AuthController {

  @Value("${app.jwt.secret}")
  private String secret;

  private final AuthenticationManager authenticationManager;

  @Operation(
      summary = "Login (username/password)",
      description = "Returns an **access token** (~1h) and a **refresh token** (~7d).")
  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public TokenPair login(@RequestBody LoginRequest body) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(body.username(), body.password()));

    String roles =
        auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    return tokenPairForUser(auth.getName(), roles);
  }

  @Operation(
      summary = "Refresh access token",
      description =
          "Accepts a **refresh token** and returns a new **access token** and **refresh token**.")
  @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
  public TokenPair refresh(@RequestBody RefreshRequest body) {
    SecretKey key = JwtKeys.hMacKey(secret);

    Claims claims =
        Jwts.parser().verifyWith(key).build().parseSignedClaims(body.refresh_token()).getPayload();

    Object typ = claims.get("typ");
    if (typ == null || !"refresh".equals(typ.toString())) {
      throw new IllegalArgumentException("Invalid token type for refresh");
    }

    String subject = claims.getSubject();
    String roles = (String) claims.getOrDefault("roles", "ROLE_USER");

    return tokenPairForUser(subject, roles);
  }

  private String makeAccessToken(SecretKey key, String subject, String roles) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .claim("roles", roles)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
        .signWith(key)
        .compact();
  }

  private String makeRefreshToken(SecretKey key, String subject) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .claim("typ", "refresh")
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
        .signWith(key)
        .compact();
  }

  private TokenPair tokenPairForUser(String username, String roles) {
    SecretKey key = JwtKeys.hMacKey(secret);
    String access = makeAccessToken(key, username, roles);
    String refresh = makeRefreshToken(key, username);
    return new TokenPair(access, refresh, "Bearer", 3600);
  }
}
