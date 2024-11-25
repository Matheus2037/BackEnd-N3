package org.example.hospitalapi.service;

import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.UpdateAppointmentDto;
import org.example.hospitalapi.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AppointmentService {

    Page<AppointmentDto> getAppointments(Pageable pageable);

    Page<AppointmentDto> getAppointmentsByStatus(StatusEnum status, Pageable pageable);

    Page<AppointmentDto> getAppointmentsByPatientId(Long patientId, Pageable pageable);

    Page<AppointmentDto> getAppointmentsByDoctorId(Long doctorId, Pageable pageable);

    AppointmentDto createAppointment(CreateAppointmentDto createAppointmentDto);

    AppointmentDto getAppointmentById(Long id);

    AppointmentDto partialUpdateAppointment(Long id, UpdateAppointmentDto updateAppointmentDto);

    void deleteAppointment(Long id);

    Page<AppointmentDto> getAppointmentsByDate(LocalDate date, Pageable pageable);
}