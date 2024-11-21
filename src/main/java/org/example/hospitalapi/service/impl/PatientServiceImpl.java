package org.example.hospitalapi.service.impl;

import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.repository.PatientRepository;
import org.example.hospitalapi.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Page<Patient> getPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null);
    }

    @Override
    public Patient updatePatient(Long id, Patient patientDetails) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setFirstName(patientDetails.getFirstName());
            patient.setEmail(patientDetails.getEmail());
            patient.setGender(patientDetails.getGender());
            return patientRepository.save(patient);
        } else {
            throw new IllegalArgumentException("Patient not found with id: " + id);
        }
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}