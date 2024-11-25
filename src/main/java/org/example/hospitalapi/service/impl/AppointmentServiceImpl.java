package org.example.hospitalapi.service.impl;

import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.UpdateAppointmentDto;
import org.example.hospitalapi.entity.Appointment;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.StatusEnum;
import org.example.hospitalapi.exceptions.NotFoundException;
import org.example.hospitalapi.mapper.AppointmentMapper;
import org.example.hospitalapi.repository.AppointmentRepository;
import org.example.hospitalapi.repository.DoctorRepository;
import org.example.hospitalapi.repository.PatientRepository;
import org.example.hospitalapi.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Page<AppointmentDto> getAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByStatus(StatusEnum status) {
        List<Appointment> appointments = appointmentRepository.findByStatus(status);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + patientId));

        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + doctorId));

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto createAppointment(CreateAppointmentDto createAppointmentDto) {
        Doctor doctor = doctorRepository.findById(createAppointmentDto.doctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + createAppointmentDto.doctorId()));

        Patient patient = patientRepository.findById(createAppointmentDto.patientId())
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + createAppointmentDto.patientId()));

        Appointment appointment = appointmentMapper.toModel(createAppointmentDto);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with id: " + id));
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public AppointmentDto partialUpdateAppointment(Long id, UpdateAppointmentDto updateAppointmentDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with id: " + id));

        if (updateAppointmentDto.appointmentDate() != null) {
            appointment.setAppointmentDate(updateAppointmentDto.appointmentDate());
        }
        if (updateAppointmentDto.status() != null) {
            appointment.setStatus(updateAppointmentDto.status());
        }

        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}