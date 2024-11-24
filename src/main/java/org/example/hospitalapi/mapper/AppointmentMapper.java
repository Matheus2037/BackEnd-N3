package org.example.hospitalapi.mapper;

import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.entity.Doctor;
import org.springframework.stereotype.Component;

import org.example.hospitalapi.entity.Appointment;

import java.util.ArrayList;

@Component
public class AppointmentMapper {
    public AppointmentDto toDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName(),
                appointment.getAppointmentDate(),
                appointment.getStatus()
        );
    }

    public Appointment toModel(CreateAppointmentDto createAppointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(createAppointmentDto.appointmentDate());
        appointment.setStatus(createAppointmentDto.status());
        return appointment;
    }
}
