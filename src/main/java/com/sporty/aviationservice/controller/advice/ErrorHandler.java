package com.sporty.aviationservice.controller.advice;

import com.sporty.aviationservice.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({GeneralException.class})
  public ResponseEntity<Object> handleKnownExceptions(
      GeneralException exception, WebRequest request) {
    var errorResponse =
        new ErrorResponse(
            exception.getHttpStatus(), exception.getErrorCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
  }
}
