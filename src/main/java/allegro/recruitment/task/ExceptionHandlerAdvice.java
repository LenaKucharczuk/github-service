package allegro.recruitment.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
class ExceptionHandlerAdvice {
  @ExceptionHandler(HttpClientErrorException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public String handleHttpClientErrorException(HttpClientErrorException exception) {
    return exception.getMessage();
  }
}
