package com.sporty.aviationservice.exception;

import com.sporty.aviationservice.domain.Error;
import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;

  private final String errorCode;

  private final HttpStatus httpStatus;

  public GeneralException(Error error, HttpStatus httpStatus) {
    super(error.getMessage());
    this.errorCode = error.getCode();
    this.httpStatus = httpStatus;
  }
}
