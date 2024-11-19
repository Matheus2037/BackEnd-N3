package org.example.hospitalapi.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByAppointmentDate(LocalDateTime date);
    List<Appointment> findByStatus(StatusEnum statusEnum);
}
