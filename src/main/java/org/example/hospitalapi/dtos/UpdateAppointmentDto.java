package org.example.hospitalapi.dtos;

import java.time.LocalDate;
import org.example.hospitalapi.enums.StatusEnum;

public record UpdateAppointmentDto(
    Long patientId,
    Long doctorId,
    LocalDate appointmentDate,
    StatusEnum status
) {

}
