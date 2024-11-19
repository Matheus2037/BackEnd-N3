package org.example.hospitalapi.repositories;

import java.util.List;
import java.util.Optional;

import org.example.hospitalapi.enums.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstName(String firstName);
    List<Patient> findByLastName(String lastName);
    List<Patient> findByEmail(String email);
    List<Patient> findByGender(GenderEnum gender);
}
