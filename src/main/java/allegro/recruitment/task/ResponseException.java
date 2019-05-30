package allegro.recruitment.task;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseException extends RuntimeException {

  private final HttpStatus httpStatus;

  ResponseException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
