package org.example.hospitalapi.service.impl;

import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.dtos.UpdateDoctorDto;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.exceptions.ResourceNotFoundException;
import org.example.hospitalapi.mapper.DoctorMapper;
import org.example.hospitalapi.repository.DoctorRepository;
import org.example.hospitalapi.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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
    public DoctorDto createDoctor(CreateDoctorDto createDoctorDto) {
        Doctor doctor = doctorMapper.toModel(createDoctorDto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorDto updateDoctor(Long id, UpdateDoctorDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

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
            throw new IllegalArgumentException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}