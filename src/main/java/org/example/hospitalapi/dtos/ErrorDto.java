package org.example.hospitalapi.dtos;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorDto(
    LocalDateTime timestamp,
    HttpStatus error,
    String message
) {

}