package com.sporty.aviationservice.controller.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

  private final HttpStatus status;
  private final String errorCode;
  private final String errorMessage;

  public ErrorResponse(HttpStatus status, String errorCode, String errorMessage) {
    super();
    this.status = status;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
