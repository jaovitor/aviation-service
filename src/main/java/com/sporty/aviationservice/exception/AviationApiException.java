package com.sporty.aviationservice.exception;

import com.sporty.aviationservice.domain.Error;
import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AviationApiException extends GeneralException {

  @Serial private static final long serialVersionUID = 1L;

  public AviationApiException(Error error, HttpStatus httpStatus) {
    super(error, httpStatus);
  }
}
