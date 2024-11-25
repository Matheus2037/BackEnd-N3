package org.example.hospitalapi.dtos;

import org.example.hospitalapi.entity.Appointment;
import org.example.hospitalapi.enums.GenderEnum;

import java.util.List;

public record PatientDto(
        long id,
        String firstName,
        String lastName,
        String email,
        GenderEnum gender,
        int totalAppointments
) {}