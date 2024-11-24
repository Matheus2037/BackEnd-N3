package org.example.hospitalapi.service.impl;

import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.dtos.UpdatePatientDto;
import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.exceptions.ResourceNotFoundException;
import org.example.hospitalapi.mapper.PatientMapper;
import org.example.hospitalapi.repository.PatientRepository;
import org.example.hospitalapi.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public Page<PatientDto> getPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }

    @Override
    public PatientDto createPatient(CreatePatientDto createPatientDto) {
        Patient patient = patientMapper.toModel(createPatientDto);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDto partialUpdatePatient(Long id, UpdatePatientDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        if (dto.firstName() != null) patient.setFirstName(dto.firstName());
        if (dto.lastName() != null) patient.setLastName(dto.lastName());
        if (dto.email() != null) patient.setEmail(dto.email());
        if (dto.gender() != null) patient.setGender(dto.gender());

        patient = patientRepository.save(patient);

        return patientMapper.toDto(patient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new IllegalArgumentException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}