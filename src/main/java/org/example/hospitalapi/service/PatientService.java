package org.example.hospitalapi.service;

import org.example.hospitalapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    Page<Patient> getPatients(Pageable pageable);

    Patient createPatient(Patient patient);

    Patient getPatientById(Long id);

    Patient updatePatient(Long id, Patient patientDetails);

    void deletePatient(Long id);

}
