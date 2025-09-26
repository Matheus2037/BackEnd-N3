package org.example.hospitalapi.service;

import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.dtos.UpdatePatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface PatientService {

    Page<PatientDto> getPatients(Pageable pageable);

    PatientDto createPatient(CreatePatientDto createPatientDto);

    ArrayList<PatientDto> createPatientList(ArrayList<CreatePatientDto> createPatientDtoList);

    PatientDto getPatientById(Long id);

    PatientDto partialUpdatePatient(Long id, UpdatePatientDto updatePatientDto);

    void deletePatient(Long id);
}