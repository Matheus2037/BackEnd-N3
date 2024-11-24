package org.example.hospitalapi.repository;

import java.util.List;

import org.example.hospitalapi.enums.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

//    TODO Implement custom queries

    List<Doctor> findByFirstName(String firstName);

    List<Doctor> findByLastName(String lastName);

    List<Doctor> findByGender(GenderEnum gender);

    List<Doctor> findByRegistration(String registration);

    List<Doctor> findByEmail(String email);
}
