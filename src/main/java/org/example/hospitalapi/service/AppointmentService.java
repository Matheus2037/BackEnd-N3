package org.example.hospitalapi.service;

import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.UpdateAppointmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.example.hospitalapi.enums.StatusEnum;

public interface AppointmentService {

    Page<AppointmentDto> getAppointments(Pageable pageable);

    AppointmentDto createAppointment(CreateAppointmentDto createAppointmentDto);

    AppointmentDto getAppointmentById(Long id);

    AppointmentDto partialUpdateAppointment(Long id, UpdateAppointmentDto updateAppointmentDto);

    void deleteAppointment(Long id);

    List<AppointmentDto> getAppointmentsByStatus(StatusEnum status);

    List<AppointmentDto> getAppointmentsByPatientId(Long patientId);

    List<AppointmentDto> getAppointmentsByDoctorId(Long doctorId);
}