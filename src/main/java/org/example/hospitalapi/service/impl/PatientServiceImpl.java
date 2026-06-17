package org.example.hospitalapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.dtos.UpdatePatientDto;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.exceptions.NotFoundException;
import org.example.hospitalapi.mapper.PatientMapper;
import org.example.hospitalapi.repository.PatientRepository;
import org.example.hospitalapi.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;

  public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
    this.patientRepository = patientRepository;
    this.patientMapper = patientMapper;
  }

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
  public ArrayList<PatientDto> createPatientList(ArrayList<CreatePatientDto> createPatientDto) {

    List<Patient> patientsToSave = createPatientDto.stream()
        .map(patientMapper::toModel)
        .collect(Collectors.toList());

    List<Patient> savedPatients = patientRepository.saveAll(patientsToSave);

    return (ArrayList<PatientDto>) savedPatients.stream()
        .map(patientMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public PatientDto getPatientById(Long id) {
    return patientMapper.toDto(findPatientOrThrow(id));
  }

  @Override
  public PatientDto partialUpdatePatient(Long id, UpdatePatientDto dto) {
    Patient patient = findPatientOrThrow(id);

    if (dto.firstName() != null) {
      patient.setFirstName(dto.firstName());
    }
    if (dto.lastName() != null) {
      patient.setLastName(dto.lastName());
    }
    if (dto.email() != null) {
      patient.setEmail(dto.email());
    }
    if (dto.gender() != null) {
      patient.setGender(dto.gender());
    }

    patient = patientRepository.save(patient);

    return patientMapper.toDto(patient);
  }

  @Override
  public void deletePatient(Long id) {
    findPatientOrThrow(id);
    patientRepository.deleteById(id);
  }

  private Patient findPatientOrThrow(Long id) {
    return patientRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));
  }
}