package org.example.hospitalapi.dtos;

import org.example.hospitalapi.enums.StatusEnum;
import java.time.LocalDate;

public record AppointmentDto(
        long id,
        long patientId,
        String patientName,
        long doctorId,
        String doctorName,
        LocalDate appointmentDate,
        StatusEnum status
) {}