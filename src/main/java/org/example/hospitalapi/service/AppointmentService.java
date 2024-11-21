package org.example.hospitalapi.service;

import org.example.hospitalapi.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    Page<Appointment> getAppointments(Pageable pageable);

    Appointment createAppointment(Appointment appointment);

    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Long id, Appointment appointmentDetails);

    void deleteAppointment(Long id);
}