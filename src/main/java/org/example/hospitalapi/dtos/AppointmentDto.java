package org.example.hospitalapi.dtos;

import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.StatusEnum;

import java.time.LocalDateTime;

public record AppointmentDto(
        long id,
        long patientId,
        String patientName,
        long doctorId,
        String doctorName,
        LocalDateTime appointmentDate,
        StatusEnum status
) {}