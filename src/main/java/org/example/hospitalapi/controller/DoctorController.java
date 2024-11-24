package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import org.example.hospitalapi.dtos.*;
import org.example.hospitalapi.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DoctorDto> getDoctors(Pageable pageable) {
        return doctorService.getDoctors(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorDto getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto createDoctor(@RequestBody @Valid CreateDoctorDto createDoctorDto) {
        return doctorService.createDoctor(createDoctorDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorDto updateDoctor(@PathVariable Long id, @RequestBody @Valid UpdateDoctorDto updateDoctorDto) {
        return doctorService.updateDoctor(id, updateDoctorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}