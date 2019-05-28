package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.net.SocketTimeoutException;

@Slf4j
@RestControllerAdvice
class ExceptionHandlerAdvice {

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<Error> handleHttpClientErrorException(HttpClientErrorException exception) {
    log.warn(exception.getMessage(), exception);
    return new ResponseEntity<>(new Error(exception.getMessage()), exception.getStatusCode());
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<Error> handleHttpServerErrorException(HttpServerErrorException exception) {
    log.warn(exception.getMessage(), exception);
    return new ResponseEntity<>(new Error(exception.getMessage()), exception.getStatusCode());
  }

  @ExceptionHandler(UnknownHttpStatusCodeException.class)
  public ResponseEntity<Error> handleUnknownHttpStatusCodeException(UnknownHttpStatusCodeException exception) {
    log.warn(exception.getMessage(), exception);
    return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SocketTimeoutException.class)
  public ResponseEntity<Error> handleSocketTimeoutException(SocketTimeoutException exception) {
    log.warn(exception.getMessage(), exception);
    return new ResponseEntity<>(new Error("It seems that fetching data from Github took too long, try again or increase socket timeout"), HttpStatus.REQUEST_TIMEOUT);
  }

  @ExceptionHandler(ConnectTimeoutException.class)
  public ResponseEntity<Error> handleSocketTimeoutException(ConnectTimeoutException exception) {
    log.warn(exception.getMessage(), exception);
    return new ResponseEntity<>(new Error("It seems that connecting to Github took too long, try again or increase connection timeout"), HttpStatus.REQUEST_TIMEOUT);
  }

  @AllArgsConstructor
  class Error {
    public final String message;
  }
}
