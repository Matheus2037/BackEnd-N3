package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import org.example.hospitalapi.dtos.*;
import org.example.hospitalapi.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AppointmentDto> getAppointments(Pageable pageable) {
        return appointmentService.getAppointments(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto createAppointment(@RequestBody @Valid CreateAppointmentDto createAppointmentDto) {
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