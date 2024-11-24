package org.example.hospitalapi.service;

import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.UpdateAppointmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    Page<AppointmentDto> getAppointments(Pageable pageable);

    AppointmentDto createAppointment(CreateAppointmentDto createAppointmentDto);

    AppointmentDto getAppointmentById(Long id);

    AppointmentDto partialUpdateAppointment(Long id, UpdateAppointmentDto updateAppointmentDto);

    void deleteAppointment(Long id);
}