package org.example.hospitalapi.mappers;

import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.entity.Doctor;
import org.springframework.stereotype.Component;

import org.example.hospitalapi.entity.Appointment;

import java.util.ArrayList;

@Component
public class AppointmentMapper {
    public AppointmentDto toDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getPatient().getId(), // ID do paciente
                appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName(), // Nome completo do paciente
                appointment.getDoctor().getId(), // ID do doutor
                appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName(), // Nome completo do doutor
                appointment.getAppointmentDate(), // Data da consulta
                appointment.getStatus() // Status da consulta
        );
    }

    public Doctor toModel(CreateDoctorDto createDoctorDto) {
        return new Doctor(
                0, // ID é gerado automaticamente, por isso pode ser inicializado como 0 ou omitido dependendo do construtor
                createDoctorDto.firstName(),
                createDoctorDto.lastName(),
                createDoctorDto.registration(),
                createDoctorDto.email(),
                createDoctorDto.gender(),
                new ArrayList<>() // Lista de appointments inicializada vazia
        );
    }
}
