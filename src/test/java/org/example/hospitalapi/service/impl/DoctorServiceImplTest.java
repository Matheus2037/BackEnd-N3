package org.example.hospitalapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.hospitalapi.dtos.CreateDoctorDto;
import org.example.hospitalapi.dtos.DoctorDto;
import org.example.hospitalapi.dtos.UpdateDoctorDto;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.enums.GenderEnum;
import org.example.hospitalapi.exceptions.NotFoundException;
import org.example.hospitalapi.mapper.DoctorMapper;
import org.example.hospitalapi.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

  @Mock
  private DoctorRepository doctorRepository;

  @Mock
  private DoctorMapper doctorMapper;

  @InjectMocks
  private DoctorServiceImpl doctorService;

  private static final Long DOCTOR_ID = 1L;

  private CreateDoctorDto buildCreateDto() {
    return new CreateDoctorDto("Pedro", "Cardoso", "12345-SC", "pedro@gmail.com", GenderEnum.MALE);
  }

  private Doctor buildDoctor() {
    return new Doctor(1L, "Pedro", "Cardoso", "12345-SC", "pedro@gmail.com", GenderEnum.MALE, List.of());
  }

  private DoctorDto buildDoctorDto() {
    return new DoctorDto(1L, "Pedro", "Cardoso", "12345-SC", "pedro@gmail.com", GenderEnum.MALE, 0);
  }

  @Test
  void shouldReturnDoctorDtoWhenCreateDoctorSucceeds() {
    CreateDoctorDto createDto = buildCreateDto();
    Doctor doctor = buildDoctor();
    DoctorDto expectedDto = buildDoctorDto();

    when(doctorMapper.toModel(createDto)).thenReturn(doctor);
    when(doctorRepository.save(doctor)).thenReturn(doctor);
    when(doctorMapper.toDto(doctor)).thenReturn(expectedDto);

    DoctorDto result = doctorService.createDoctor(createDto);

    assertThat(result).isEqualTo(expectedDto);
    verify(doctorMapper).toModel(createDto);
    verify(doctorRepository).save(doctor);
    verify(doctorMapper).toDto(doctor);
  }

  @Test
  void shouldReturnDoctorDtoListWhenCreateDoctorsSucceeds() {
    CreateDoctorDto createDto = buildCreateDto();
    Doctor doctor = buildDoctor();
    DoctorDto doctorDto = buildDoctorDto();
    List<CreateDoctorDto> createDtoList = List.of(createDto);
    List<Doctor> doctorList = List.of(doctor);

    when(doctorMapper.toModel(createDto)).thenReturn(doctor);
    when(doctorRepository.saveAll(doctorList)).thenReturn(doctorList);
    when(doctorMapper.toDto(doctor)).thenReturn(doctorDto);

    List<DoctorDto> result = doctorService.createDoctors(createDtoList);

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(doctorDto);
    verify(doctorRepository).saveAll(doctorList);
  }

  @Test
  void shouldReturnDoctorDtoWhenFindByIdSucceeds() {
    Doctor doctor = buildDoctor();
    DoctorDto expectedDto = buildDoctorDto();

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
    when(doctorMapper.toDto(doctor)).thenReturn(expectedDto);

    DoctorDto result = doctorService.getDoctorById(DOCTOR_ID);

    assertThat(result).isEqualTo(expectedDto);
    verify(doctorRepository).findById(DOCTOR_ID);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenFindByIdNotFound() {
    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> doctorService.getDoctorById(DOCTOR_ID))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(DOCTOR_ID.toString());
  }

  @Test
  void shouldReturnPageOfDoctorDtoWhenFindAllSucceeds() {
    Doctor doctor = buildDoctor();
    DoctorDto doctorDto = buildDoctorDto();
    Pageable pageable = PageRequest.of(0, 10);
    Page<Doctor> doctorPage = new PageImpl<>(List.of(doctor), pageable, 1);

    when(doctorRepository.findAll(pageable)).thenReturn(doctorPage);
    when(doctorMapper.toDto(doctor)).thenReturn(doctorDto);

    Page<DoctorDto> result = doctorService.getDoctors(pageable);

    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(doctorDto);
    verify(doctorRepository).findAll(pageable);
  }

  @Test
  void shouldReturnUpdatedDoctorDtoWhenUpdateDoctorSucceeds() {
    Doctor doctor = buildDoctor();
    UpdateDoctorDto updateDto = new UpdateDoctorDto("João", null, "joao@gmail.com", null);
    DoctorDto updatedDto = new DoctorDto(1L, "João", "Cardoso", "12345-SC", "joao@gmail.com", GenderEnum.MALE, 0);

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
    when(doctorRepository.save(doctor)).thenReturn(doctor);
    when(doctorMapper.toDto(doctor)).thenReturn(updatedDto);

    DoctorDto result = doctorService.partialUpdateDoctor(DOCTOR_ID, updateDto);

    assertThat(result).isEqualTo(updatedDto);
    assertThat(doctor.getFirstName()).isEqualTo("João");
    assertThat(doctor.getEmail()).isEqualTo("joao@gmail.com");
    assertThat(doctor.getLastName()).isEqualTo("Cardoso");
    verify(doctorRepository).save(doctor);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenUpdateDoctorNotFound() {
    UpdateDoctorDto updateDto = new UpdateDoctorDto("João", null, null, null);

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> doctorService.partialUpdateDoctor(DOCTOR_ID, updateDto))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(DOCTOR_ID.toString());
  }

  @Test
  void shouldCallDeleteByIdWhenDeleteDoctorSucceeds() {
    Doctor doctor = buildDoctor();

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));

    doctorService.deleteDoctor(DOCTOR_ID);

    verify(doctorRepository).deleteById(DOCTOR_ID);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenDeleteDoctorNotFound() {
    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> doctorService.deleteDoctor(DOCTOR_ID))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(DOCTOR_ID.toString());
  }
}
