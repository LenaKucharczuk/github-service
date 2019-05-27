package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@RestControllerAdvice
class ExceptionHandlerAdvice {
  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<Error> handleHttpClientErrorException(HttpClientErrorException exception) {
    return new ResponseEntity<>(new Error(exception.getMessage()), exception.getStatusCode());
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<Error> handleHttpServerErrorException(HttpServerErrorException exception) {
    return new ResponseEntity<>(new Error(exception.getMessage()), exception.getStatusCode());
  }

  @ExceptionHandler(UnknownHttpStatusCodeException.class)
  public ResponseEntity<Error> handleUnknownHttpStatusCodeException(UnknownHttpStatusCodeException exception) {
    return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.NOT_FOUND);
  }

  @AllArgsConstructor
  class Error {
    public final String message;
  }
}
