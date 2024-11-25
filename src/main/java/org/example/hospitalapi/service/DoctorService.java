package org.example.hospitalapi.service;

import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.dtos.UpdateDoctorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    Page<DoctorDto> getDoctors(Pageable pageable);

    DoctorDto createDoctor(CreateDoctorDto createDoctorDto);

    DoctorDto getDoctorById(Long id);

    Page<DoctorDto> getDoctorsByFirstName(String firstName, Pageable pageable);

    Page<DoctorDto> getDoctorsByRegistration(String registration, Pageable pageable);

    Page<DoctorDto> getDoctorsByEmail(String email, Pageable pageable);

    DoctorDto partialUpdateDoctor(Long id, UpdateDoctorDto updateDoctorDto);

    void deleteDoctor(Long id);
}