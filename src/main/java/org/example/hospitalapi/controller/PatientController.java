package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.dtos.UpdatePatientDto;
import org.example.hospitalapi.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PatientDto> getPatients(Pageable pageable) {
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientDto updatePatient(@PathVariable Long id, @RequestBody @Valid UpdatePatientDto updatePatientDto) {
        return patientService.updatePatient(id, updatePatientDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}