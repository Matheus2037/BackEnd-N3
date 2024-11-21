package org.example.hospitalapi.service;

import org.example.hospitalapi.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    Page<Doctor> getDoctors(Pageable pageable);

    Doctor createDoctor(Doctor doctor);

    Doctor getDoctorById(Long id);

    Doctor updateDoctor(Long id, Doctor doctorDetails);

    void deleteDoctor(Long id);

}
