package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.dtos.UpdatePatientDto;
import org.example.hospitalapi.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<PatientDto> getPatients(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return patientService.getPatients(pageable);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PatientDto getPatientById(@PathVariable Long id) {
    return patientService.getPatientById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PatientDto createPatient(@RequestBody @Valid CreatePatientDto createPatientDto) {
    return patientService.createPatient(createPatientDto);
  }

  @PostMapping("/batch")
  public ResponseEntity<List<PatientDto>> createPatientList(
      @RequestBody @Valid List<CreatePatientDto> createPatientList) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(patientService.createPatientList(createPatientList));
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PatientDto partialUpdatePatient(@PathVariable Long id,
      @RequestBody @Valid UpdatePatientDto updatePatientDto) {
    return patientService.partialUpdatePatient(id, updatePatientDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePatient(@PathVariable Long id) {
    patientService.deletePatient(id);
  }
}