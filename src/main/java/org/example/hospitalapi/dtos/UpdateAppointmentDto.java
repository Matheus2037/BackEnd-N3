package org.example.hospitalapi.dtos;

import org.example.hospitalapi.enums.StatusEnum;

import java.time.LocalDateTime;

public record UpdateAppointmentDto(
        long patientId,
        long doctorId,
        LocalDateTime appointmentDate,
        StatusEnum status
) {}
