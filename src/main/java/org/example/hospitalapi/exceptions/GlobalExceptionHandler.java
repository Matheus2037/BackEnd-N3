package org.example.hospitalapi.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.example.hospitalapi.dtos.ErrorDto;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorDto handleNotFoundException(NotFoundException e) {
    return new ErrorDto(e.getTimestamp(), e.getStatus(), e.getMensagem());
  }

  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorDto handleConflictException(ConflictException e) {
    return new ErrorDto(e.getTimestamp(), e.getStatus(), e.getMensagem());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorDto handleBadRequestException(BadRequestException e) {
    return new ErrorDto(e.getTimestamp(), e.getStatus(), e.getMensagem());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorDto handleValidationException(MethodArgumentNotValidException e) {
    return new ErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getAllErrors().get(0).getDefaultMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
    return new ErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage());
  }
}
