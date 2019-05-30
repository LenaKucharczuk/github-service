package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
class ExceptionHandlerAdvice {

  @ExceptionHandler(ResponseException.class)
  public ResponseEntity<Error> handleResponseException(ResponseException exception) {
    log.warn(exception.getMessage(), exception);
    return new ResponseEntity<>(new Error(exception.getMessage()), exception.getHttpStatus());
  }

  @AllArgsConstructor
  class Error {
    public final String message;
  }
}
