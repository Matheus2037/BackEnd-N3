package org.example.hospitalapi.repository;

import java.time.LocalDate;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);

    Page<Appointment> findByPatient(Patient patient, Pageable pageable);

    Page<Appointment> findByAppointmentDate(LocalDate date, Pageable pageable);

    Page<Appointment> findByStatus(StatusEnum statusEnum, Pageable pageable);
}