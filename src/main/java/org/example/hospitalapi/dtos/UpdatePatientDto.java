package org.example.hospitalapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.example.hospitalapi.enums.GenderEnum;

public record UpdatePatientDto(
        @Size(max = 50) String firstName,
        @Size(max = 50) String lastName,
        @Email String email,
        GenderEnum gender
) {}
