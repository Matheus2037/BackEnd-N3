package org.example.hospitalapi.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.example.hospitalapi.validation.EntityExists;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.enums.StatusEnum;

import java.time.LocalDateTime;

public record CreateAppointmentDto(

        @EntityExists(
                entity = Patient.class,
                message = "O paciente fornecido não existe"
        )
        long patientId,

        @EntityExists(
                entity = Doctor.class,
                message = "O médico fornecido não existe"
        )
        long doctorId,

        @Future(
                message = "A data da consulta deve ser maior que a atual"
        )
        LocalDateTime appointmentDate,
        @NotNull(
                message = "O campo status deve ser uma das seguintes opções: (Scheduled, Completed, Canceled, Rescheduled, Pending Confirmation)"
        )
        StatusEnum status

) {}
