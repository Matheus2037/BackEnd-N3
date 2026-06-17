package org.example.hospitalapi.service.impl;

import java.time.LocalDate;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;
  private final AppointmentMapper appointmentMapper;

  public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
      DoctorRepository doctorRepository,
      PatientRepository patientRepository,
      AppointmentMapper appointmentMapper) {
    this.appointmentRepository = appointmentRepository;
    this.doctorRepository = doctorRepository;
    this.patientRepository = patientRepository;
    this.appointmentMapper = appointmentMapper;
  }

  @Override
  public Page<AppointmentDto> getAppointments(Pageable pageable) {
    return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
  }

  @Override
  public Page<AppointmentDto> getAppointmentsByStatus(StatusEnum status, Pageable pageable) {
    return appointmentRepository.findByStatus(status, pageable).map(appointmentMapper::toDto);
  }

  @Override
  public Page<AppointmentDto> getAppointmentsByPatientId(Long patientId, Pageable pageable) {
    Patient patient = findPatientOrThrow(patientId);
    return appointmentRepository.findByPatient(patient, pageable).map(appointmentMapper::toDto);
  }

  @Override
  public Page<AppointmentDto> getAppointmentsByDoctorId(Long doctorId, Pageable pageable) {
    Doctor doctor = findDoctorOrThrow(doctorId);
    return appointmentRepository.findByDoctor(doctor, pageable).map(appointmentMapper::toDto);
  }

  @Override
  public AppointmentDto createAppointment(CreateAppointmentDto createAppointmentDto) {
    Doctor doctor = findDoctorOrThrow(createAppointmentDto.doctorId());
    Patient patient = findPatientOrThrow(createAppointmentDto.patientId());
    Appointment appointment = appointmentMapper.toModel(createAppointmentDto);
    appointment.setDoctor(doctor);
    appointment.setPatient(patient);
    appointment = appointmentRepository.save(appointment);
    return appointmentMapper.toDto(appointment);
  }

  @Override
  public AppointmentDto getAppointmentById(Long id) {
    return appointmentMapper.toDto(findAppointmentOrThrow(id));
  }

  @Override
  public AppointmentDto partialUpdateAppointment(Long id,
      UpdateAppointmentDto updateAppointmentDto) {
    Appointment appointment = findAppointmentOrThrow(id);
    if (updateAppointmentDto.patientId() != null) {
      appointment.setPatient(findPatientOrThrow(updateAppointmentDto.patientId()));
    }
    if (updateAppointmentDto.doctorId() != null) {
      appointment.setDoctor(findDoctorOrThrow(updateAppointmentDto.doctorId()));
    }
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
    findAppointmentOrThrow(id);
    appointmentRepository.deleteById(id);
  }

  @Override
  public Page<AppointmentDto> getAppointmentsByDate(LocalDate date, Pageable pageable) {
    return appointmentRepository.findByAppointmentDate(date, pageable)
        .map(appointmentMapper::toDto);
  }

  private Appointment findAppointmentOrThrow(Long id) {
    return appointmentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Appointment not found with id: " + id));
  }

  private Doctor findDoctorOrThrow(Long id) {
    return doctorRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + id));
  }

  private Patient findPatientOrThrow(Long id) {
    return patientRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));
  }
}