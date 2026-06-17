package org.example.hospitalapi.dtos;

import org.example.hospitalapi.enums.GenderEnum;

public record PatientDto(
    long id,
    String firstName,
    String lastName,
    String email,
    GenderEnum gender,
    int totalAppointments
) {

}