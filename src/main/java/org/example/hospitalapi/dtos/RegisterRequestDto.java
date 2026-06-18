package org.example.hospitalapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
    @NotBlank
    String username,
    @NotBlank
    String password
) {

}
