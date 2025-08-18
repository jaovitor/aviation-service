package com.sporty.aviationservice.domain;

import lombok.Getter;

@Getter
public enum Error {
  AVIATION_API_ERROR("0001", "Aviation API error"),
  AIRPORT_NOT_FOUND("0002", "Airport not found");

  final String code;
  final String message;

  Error(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
