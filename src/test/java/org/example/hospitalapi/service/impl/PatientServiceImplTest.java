package org.example.hospitalapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.hospitalapi.dtos.CreatePatientDto;
import org.example.hospitalapi.dtos.PatientDto;
import org.example.hospitalapi.dtos.UpdatePatientDto;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.GenderEnum;
import org.example.hospitalapi.exceptions.NotFoundException;
import org.example.hospitalapi.mapper.PatientMapper;
import org.example.hospitalapi.repository.PatientRepository;
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
class PatientServiceImplTest {

  @Mock
  private PatientRepository patientRepository;

  @Mock
  private PatientMapper patientMapper;

  @InjectMocks
  private PatientServiceImpl patientService;

  private static final Long PATIENT_ID = 1L;
  private static final String PATIENT_EMAIL = "fernanda@email.com";

  private CreatePatientDto buildCreateDto() {
    return new CreatePatientDto("Fernanda", "Martins", PATIENT_EMAIL, GenderEnum.FEMALE);
  }

  private Patient buildPatient() {
    return new Patient(1L, "Fernanda", "Martins", PATIENT_EMAIL, GenderEnum.FEMALE, List.of());
  }

  private PatientDto buildPatientDto() {
    return new PatientDto(1L, "Fernanda", "Martins", PATIENT_EMAIL, GenderEnum.FEMALE, 0);
  }

  @Test
  void shouldReturnPatientDtoWhenCreatePatientSucceeds() {
    CreatePatientDto createDto = buildCreateDto();
    Patient patient = buildPatient();
    PatientDto expectedDto = buildPatientDto();

    when(patientMapper.toModel(createDto)).thenReturn(patient);
    when(patientRepository.save(patient)).thenReturn(patient);
    when(patientMapper.toDto(patient)).thenReturn(expectedDto);

    PatientDto result = patientService.createPatient(createDto);

    assertThat(result).isEqualTo(expectedDto);
    verify(patientMapper).toModel(createDto);
    verify(patientRepository).save(patient);
    verify(patientMapper).toDto(patient);
  }

  @Test
  void shouldReturnPatientDtoListWhenCreatePatientListSucceeds() {
    CreatePatientDto createDto = buildCreateDto();
    Patient patient = buildPatient();
    PatientDto patientDto = buildPatientDto();
    List<CreatePatientDto> createDtoList = List.of(createDto);
    List<Patient> patientList = List.of(patient);

    when(patientMapper.toModel(createDto)).thenReturn(patient);
    when(patientRepository.saveAll(patientList)).thenReturn(patientList);
    when(patientMapper.toDto(patient)).thenReturn(patientDto);

    List<PatientDto> result = patientService.createPatientList(createDtoList);

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(patientDto);
    verify(patientRepository).saveAll(patientList);
  }

  @Test
  void shouldReturnPatientDtoWhenFindByIdSucceeds() {
    Patient patient = buildPatient();
    PatientDto expectedDto = buildPatientDto();

    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
    when(patientMapper.toDto(patient)).thenReturn(expectedDto);

    PatientDto result = patientService.getPatientById(PATIENT_ID);

    assertThat(result).isEqualTo(expectedDto);
    verify(patientRepository).findById(PATIENT_ID);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenFindByIdNotFound() {
    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> patientService.getPatientById(PATIENT_ID))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(PATIENT_ID.toString());
  }

  @Test
  void shouldReturnPageOfPatientDtoWhenFindAllSucceeds() {
    Patient patient = buildPatient();
    PatientDto patientDto = buildPatientDto();
    Pageable pageable = PageRequest.of(0, 10);
    Page<Patient> patientPage = new PageImpl<>(List.of(patient), pageable, 1);

    when(patientRepository.findAll(pageable)).thenReturn(patientPage);
    when(patientMapper.toDto(patient)).thenReturn(patientDto);

    Page<PatientDto> result = patientService.getPatients(pageable);

    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(patientDto);
    verify(patientRepository).findAll(pageable);
  }

  @Test
  void shouldReturnFilteredPageWhenFindByNameSucceeds() {
    Patient patient = buildPatient();
    PatientDto patientDto = buildPatientDto();
    Pageable pageable = PageRequest.of(0, 10);
    Page<Patient> patientPage = new PageImpl<>(List.of(patient), pageable, 1);

    when(patientRepository.findByFirstName("Fernanda", pageable)).thenReturn(patientPage);
    when(patientMapper.toDto(patient)).thenReturn(patientDto);

    Page<PatientDto> result = patientService.getPatientsByFirstName("Fernanda", pageable);

    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(patientDto);
    verify(patientRepository).findByFirstName("Fernanda", pageable);
  }

  @Test
  void shouldReturnPatientDtoWhenFindByEmailSucceeds() {
    Patient patient = buildPatient();
    PatientDto expectedDto = buildPatientDto();

    when(patientRepository.findByEmail(PATIENT_EMAIL)).thenReturn(Optional.of(patient));
    when(patientMapper.toDto(patient)).thenReturn(expectedDto);

    PatientDto result = patientService.getPatientByEmail(PATIENT_EMAIL);

    assertThat(result).isEqualTo(expectedDto);
    verify(patientRepository).findByEmail(PATIENT_EMAIL);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenFindByEmailNotFound() {
    when(patientRepository.findByEmail(PATIENT_EMAIL)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> patientService.getPatientByEmail(PATIENT_EMAIL))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(PATIENT_EMAIL);
  }

  @Test
  void shouldReturnUpdatedPatientDtoWhenUpdatePatientSucceeds() {
    Patient patient = buildPatient();
    UpdatePatientDto updateDto = new UpdatePatientDto("Juliana", null, "juliana@email.com", null);
    PatientDto updatedDto = new PatientDto(1L, "Juliana", "Martins", "juliana@email.com", GenderEnum.FEMALE, 0);

    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
    when(patientRepository.save(patient)).thenReturn(patient);
    when(patientMapper.toDto(patient)).thenReturn(updatedDto);

    PatientDto result = patientService.partialUpdatePatient(PATIENT_ID, updateDto);

    assertThat(result).isEqualTo(updatedDto);
    assertThat(patient.getFirstName()).isEqualTo("Juliana");
    assertThat(patient.getEmail()).isEqualTo("juliana@email.com");
    assertThat(patient.getLastName()).isEqualTo("Martins");
    verify(patientRepository).save(patient);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenUpdatePatientNotFound() {
    UpdatePatientDto updateDto = new UpdatePatientDto("Juliana", null, null, null);

    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> patientService.partialUpdatePatient(PATIENT_ID, updateDto))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(PATIENT_ID.toString());
  }

  @Test
  void shouldCallDeleteByIdWhenDeletePatientSucceeds() {
    Patient patient = buildPatient();

    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));

    patientService.deletePatient(PATIENT_ID);

    verify(patientRepository).deleteById(PATIENT_ID);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenDeletePatientNotFound() {
    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> patientService.deletePatient(PATIENT_ID))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(PATIENT_ID.toString());
  }
}
