package org.example.hospitalapi.repository;

import org.example.hospitalapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
