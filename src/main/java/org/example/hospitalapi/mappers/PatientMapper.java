package org.example.hospitalapi.mappers;

import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.springframework.stereotype.Component;

import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.entity.Patient;

import java.util.ArrayList;

@Component
public class PatientMapper {
    public PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getGender(),
                patient.getAppointments().size()
        );
    }

    public Patient toModel(CreatePatientDto createPatientDto) {
        return new Patient(
                0, // ID é gerado automaticamente, por isso pode ser inicializado como 0 ou omitido dependendo do construtor
                createPatientDto.firstName(),
                createPatientDto.lastName(),
                createPatientDto.email(),
                createPatientDto.gender(),
                new ArrayList<>() // Lista de appointments inicializada vazia
        );
    }

}