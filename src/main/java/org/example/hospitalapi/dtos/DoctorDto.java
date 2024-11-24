package org.example.hospitalapi.dtos;

import org.example.hospitalapi.enums.GenderEnum;


public record DoctorDto(
        long id,
        String firstName,
        String lastName,
        String registration,
        String email,
        GenderEnum gender,
        int totalAppointments
) {}