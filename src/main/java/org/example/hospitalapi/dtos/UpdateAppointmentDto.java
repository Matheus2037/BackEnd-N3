package org.example.hospitalapi.dtos;

import org.example.hospitalapi.enums.StatusEnum;

import java.time.LocalDate;

public record UpdateAppointmentDto(
        long patientId,
        long doctorId,
        LocalDate appointmentDate,
        StatusEnum status
) {}
