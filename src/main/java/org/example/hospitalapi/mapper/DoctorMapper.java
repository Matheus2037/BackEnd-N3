package org.example.hospitalapi.mapper;

import java.util.ArrayList;
import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.entity.Doctor;
import org.springframework.stereotype.Component;

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
    Doctor doctor = new Doctor();

    doctor.setFirstName(createDoctorDto.firstName());
    doctor.setLastName(createDoctorDto.lastName());
    doctor.setRegistration(createDoctorDto.registration());
    doctor.setEmail(createDoctorDto.email());
    doctor.setGender(createDoctorDto.gender());
    doctor.setAppointments(new ArrayList<>());

    return doctor;
  }
}