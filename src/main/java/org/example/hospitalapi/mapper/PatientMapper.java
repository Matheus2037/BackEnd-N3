package org.example.hospitalapi.mapper;

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
        Patient patient = new Patient();
        patient.setFirstName(createPatientDto.firstName());
        patient.setLastName(createPatientDto.lastName());
        patient.setEmail(createPatientDto.email());
        patient.setGender(createPatientDto.gender());
        patient.setAppointments(new ArrayList<>());
        return patient;
    }

}