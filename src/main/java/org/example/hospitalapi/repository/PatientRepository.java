package org.example.hospitalapi.repository;

import java.util.Optional;
import org.example.hospitalapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

  Page<Patient> findByFirstName(String firstName, Pageable pageable);

  Optional<Patient> findByEmail(String email);
}
