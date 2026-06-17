package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.dtos.UpdateDoctorDto;
import org.example.hospitalapi.service.DoctorService;
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
@RequestMapping("/doctor")
public class DoctorController {

  private final DoctorService doctorService;

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

  @PostMapping("/batch")
  @ResponseStatus(HttpStatus.CREATED)
  public ArrayList<DoctorDto> createDoctors(
      @RequestBody @Valid ArrayList<CreateDoctorDto> createDoctorDtoList) {
    return doctorService.createDoctors(createDoctorDtoList);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public DoctorDto partialUpdateDoctor(@PathVariable Long id,
      @RequestBody @Valid UpdateDoctorDto updateDoctorDto) {
    return doctorService.partialUpdateDoctor(id, updateDoctorDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDoctor(@PathVariable Long id) {
    doctorService.deleteDoctor(id);
  }
}