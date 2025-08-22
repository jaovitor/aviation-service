package com.sporty.aviationservice.controller.auth;

public record TokenPair(
    String access_token, String refresh_token, String token_type, long expires_in) {}
