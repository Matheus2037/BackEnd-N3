package org.example.hospitalapi.repositories;

import java.util.List;
import java.util.Optional;

import org.example.hospitalapi.enums.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByFirstName(String firstName);
    List<Doctor> findByLastName(String lastName);
    List<Doctor> findByGender(GenderEnum gender);
    List<Doctor> findByRegistration(String registration);
    List<Doctor> findByEmail(String email);
}
