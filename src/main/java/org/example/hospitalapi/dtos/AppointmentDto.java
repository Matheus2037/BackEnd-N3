package org.example.hospitalapi.dtos;

import java.time.LocalDate;
import org.example.hospitalapi.enums.StatusEnum;

public record AppointmentDto(
    long id,
    long patientId,
    String patientName,
    long doctorId,
    String doctorName,
    LocalDate appointmentDate,
    StatusEnum status
) {

}