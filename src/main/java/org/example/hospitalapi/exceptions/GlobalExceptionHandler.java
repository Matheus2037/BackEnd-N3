package org.example.hospitalapi.exceptions;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.example.hospitalapi.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handleNotFoundException(NotFoundException e) {
    return new ErrorDto(e.getTimestamp(), e.getStatus(), e.getMessage());
  }

  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorDto handleConflictException(ConflictException e) {
    return new ErrorDto(e.getTimestamp(), e.getStatus(), e.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleBadRequestException(BadRequestException e) {
    return new ErrorDto(e.getTimestamp(), e.getStatus(), e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleValidationException(MethodArgumentNotValidException e) {
    return new ErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
        e.getAllErrors().get(0).getDefaultMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
    return new ErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage());
  }
}
