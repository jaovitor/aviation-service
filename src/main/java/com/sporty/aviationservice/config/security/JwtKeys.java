package com.sporty.aviationservice.config.security;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

public final class JwtKeys {
  private JwtKeys() {}

  public static SecretKey hMacKey(String secret) {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }
}
