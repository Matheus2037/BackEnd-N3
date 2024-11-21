package org.example.hospitalapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.hospitalapi.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    TODO Implement custom queries

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByAppointmentDate(LocalDateTime date);

    List<Appointment> findByStatus(StatusEnum statusEnum);

}
