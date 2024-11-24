package org.example.hospitalapi.dtos;

import org.example.hospitalapi.enums.GenderEnum;

public record UpdateDoctorDto(
        String firstName,
        String lastName,
        String email,
        GenderEnum gender
) {}
