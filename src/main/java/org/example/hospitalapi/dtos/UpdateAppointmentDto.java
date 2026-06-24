package org.example.hospitalapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import org.example.hospitalapi.enums.StatusEnum;

public record UpdateAppointmentDto(
    Long patientId,
    Long doctorId,
    LocalDate appointmentDate,
    @Schema(example = "SCHEDULED")
    StatusEnum status
) {

}
