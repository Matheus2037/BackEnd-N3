package org.example.hospitalapi.repository;

import java.util.List;

import org.example.hospitalapi.enums.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findByFirstName(String firstName, Pageable pageable);

    Page<Doctor> findByRegistration(String registration, Pageable pageable);

    Page<Doctor> findByEmail(String email, Pageable pageable);
}
