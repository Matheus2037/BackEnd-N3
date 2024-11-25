package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import org.example.hospitalapi.dtos.*;
import org.example.hospitalapi.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<DoctorDto> getDoctors(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.getDoctors(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorDto getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/first-name/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public Page<DoctorDto> getDoctorsByFirstName(@PathVariable String firstName,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.getDoctorsByFirstName(firstName, pageable);
    }

    @GetMapping("/registration/{registration}")
    @ResponseStatus(HttpStatus.OK)
    public Page<DoctorDto> getDoctorsByRegistration(@PathVariable String registration,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.getDoctorsByRegistration(registration, pageable);
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Page<DoctorDto> getDoctorsByEmail(@PathVariable String email,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.getDoctorsByEmail(email, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto createDoctor(@RequestBody @Valid CreateDoctorDto createDoctorDto) {
        return doctorService.createDoctor(createDoctorDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorDto partialUpdateDoctor(@PathVariable Long id, @RequestBody @Valid UpdateDoctorDto updateDoctorDto) {
        return doctorService.partialUpdateDoctor(id, updateDoctorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}