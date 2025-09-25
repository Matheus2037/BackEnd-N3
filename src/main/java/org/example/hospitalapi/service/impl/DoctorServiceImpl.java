package org.example.hospitalapi.service.impl;

import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.dtos.UpdateDoctorDto;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.exceptions.NotFoundException;
import org.example.hospitalapi.mapper.DoctorMapper;
import org.example.hospitalapi.repository.DoctorRepository;
import org.example.hospitalapi.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public Page<DoctorDto> getDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(doctorMapper::toDto);
    }

    @Override
    public Page<DoctorDto> getDoctorsByFirstName(String firstName, Pageable pageable) {
        return doctorRepository.findByFirstName(firstName, pageable).map(doctorMapper::toDto);
    }

    @Override
    public Page<DoctorDto> getDoctorsByRegistration(String registration, Pageable pageable) {
        return doctorRepository.findByRegistration(registration, pageable).map(doctorMapper::toDto);
    }

    @Override
    public Page<DoctorDto> getDoctorsByEmail(String email, Pageable pageable) {
        return doctorRepository.findByEmail(email, pageable).map(doctorMapper::toDto);
    }

    @Override
    public DoctorDto createDoctor(CreateDoctorDto createDoctorDto) {
        Doctor doctor = doctorMapper.toModel(createDoctorDto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public ArrayList<DoctorDto> createDoctors(ArrayList<CreateDoctorDto> createDoctorDtoList) {

        ArrayList<DoctorDto> doctorDtoList =  new ArrayList<>();

        for (CreateDoctorDto createDoctorDto : createDoctorDtoList) {
            Doctor doctor = doctorMapper.toModel(createDoctorDto);
            doctorDtoList.add(doctorMapper.toDto(doctor));
            doctorMapper.toDto(doctorRepository.save(doctor));
        }

        return doctorDtoList;
    }

    @Override
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + id));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorDto partialUpdateDoctor(Long id, UpdateDoctorDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + id));

        if (dto.firstName() != null) doctor.setFirstName(dto.firstName());
        if (dto.lastName() != null) doctor.setLastName(dto.lastName());
        if (dto.email() != null) doctor.setEmail(dto.email());
        if (dto.gender() != null) doctor.setGender(dto.gender());

        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new NotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}