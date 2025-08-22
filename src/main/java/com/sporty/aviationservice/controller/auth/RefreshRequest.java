package com.sporty.aviationservice.controller.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(@NotBlank String refresh_token) {}
