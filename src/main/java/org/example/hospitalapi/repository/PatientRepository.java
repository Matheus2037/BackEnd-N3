package org.example.hospitalapi.repository;

import java.util.List;

import org.example.hospitalapi.enums.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

//    TODO Implement custom queries

    List<Patient> findByFirstName(String firstName);

    List<Patient> findByLastName(String lastName);

    List<Patient> findByEmail(String email);

    List<Patient> findByGender(GenderEnum gender);
}
