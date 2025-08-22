package com.sporty.aviationservice.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "Admin-only operations. Roles: **ROLE_ADMIN**")
@RestController
public class AdminController {

  @Operation(summary = "Admin ping", description = "Responds only if you have **ROLE_ADMIN**.")
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin/ping")
  public Map<String, String> adminPing() {
    return Map.of("ok", "pong (admin)");
  }
}
