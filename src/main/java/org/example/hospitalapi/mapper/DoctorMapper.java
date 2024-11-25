package org.example.hospitalapi.mapper;

import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.springframework.stereotype.Component;

import org.example.hospitalapi.entity.Doctor;

import java.util.ArrayList;

@Component
public class DoctorMapper {
    public DoctorDto toDto(Doctor doctor) {
        return new DoctorDto(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getRegistration(),
                doctor.getEmail(),
                doctor.getGender(),
                doctor.getAppointments().size()
        );
    }

    public Doctor toModel(CreateDoctorDto createDoctorDto) {
        return new Doctor(
                0,
                createDoctorDto.firstName(),
                createDoctorDto.lastName(),
                createDoctorDto.registration(),
                createDoctorDto.email(),
                createDoctorDto.gender(),
                new ArrayList<>()
        );
    }
}