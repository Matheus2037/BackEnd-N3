package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.UpdateAppointmentDto;
import org.example.hospitalapi.enums.StatusEnum;
import org.example.hospitalapi.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<AppointmentDto> getAppointments(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return appointmentService.getAppointments(pageable);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public AppointmentDto getAppointmentById(@PathVariable Long id) {
    return appointmentService.getAppointmentById(id);
  }

  @GetMapping("/date/{date}")
  @ResponseStatus(HttpStatus.OK)
  public Page<AppointmentDto> getAppointmentsByDate(@PathVariable LocalDate date,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return appointmentService.getAppointmentsByDate(date, pageable);
  }

  @GetMapping("/status/{status}")
  public Page<AppointmentDto> getAppointmentsByStatus(@PathVariable StatusEnum status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return appointmentService.getAppointmentsByStatus(status, pageable);
  }

  @GetMapping("/patient/{patientId}")
  public Page<AppointmentDto> getAppointmentsByPatientId(@PathVariable Long patientId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return appointmentService.getAppointmentsByPatientId(patientId, pageable);
  }

  @GetMapping("/doctor/{doctorId}")
  public Page<AppointmentDto> getAppointmentsByDoctorId(@PathVariable Long doctorId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return appointmentService.getAppointmentsByDoctorId(doctorId, pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AppointmentDto createAppointment(
      @RequestBody @Valid CreateAppointmentDto createAppointmentDto) {
    return appointmentService.createAppointment(createAppointmentDto);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public AppointmentDto partialUpdateAppointment(
      @PathVariable Long id,
      @RequestBody @Valid UpdateAppointmentDto updateAppointmentDto) {
    return appointmentService.partialUpdateAppointment(id, updateAppointmentDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAppointment(@PathVariable Long id) {
    appointmentService.deleteAppointment(id);
  }
}