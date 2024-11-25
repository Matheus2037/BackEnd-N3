package org.example.hospitalapi.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
  private final String mensagem;
  private final HttpStatus status;
  private final LocalDateTime timestamp;

  public ServiceException(String mensagem, HttpStatus status) {
    super(mensagem);
    this.mensagem = mensagem;
    this.status = status;
    this.timestamp = LocalDateTime.now();
  }

  public String getMensagem() {
    return mensagem;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}
