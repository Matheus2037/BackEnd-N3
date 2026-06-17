package org.example.hospitalapi.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

  private final HttpStatus status;
  private final LocalDateTime timestamp;

  public ServiceException(String message, HttpStatus status) {
    super(message);
    this.status = status;
    this.timestamp = LocalDateTime.now();
  }

  public HttpStatus getStatus() {
    return status;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}
