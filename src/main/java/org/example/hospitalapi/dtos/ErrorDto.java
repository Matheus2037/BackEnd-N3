package org.example.hospitalapi.dtos;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorDto(
        LocalDateTime timestamp,
        HttpStatus error,
        String message
) {}