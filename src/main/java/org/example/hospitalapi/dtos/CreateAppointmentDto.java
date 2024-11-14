package org.example.hospitalapi.dtos;

import jakarta.validation.constraints.NotNull;
import org.example.hospitalapi.enums.StatusEnum;

import java.time.LocalDateTime;

public record CreateAppointmentDto(

        long patientId,
        String patientName,
        long doctorId,
        String doctorName,
        LocalDateTime appointmentDate,
        @NotNull(
                message = "O campo status deve ser uma das seguintes opções: (Scheduled,Completed,Canceled)"
        )
        StatusEnum status

) {}
