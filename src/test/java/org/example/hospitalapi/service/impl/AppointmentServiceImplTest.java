package org.example.hospitalapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.example.hospitalapi.dtos.AppointmentDto;
import org.example.hospitalapi.dtos.CreateAppointmentDto;
import org.example.hospitalapi.dtos.UpdateAppointmentDto;
import org.example.hospitalapi.entity.Appointment;
import org.example.hospitalapi.entity.Doctor;
import org.example.hospitalapi.entity.Patient;
import org.example.hospitalapi.enums.GenderEnum;
import org.example.hospitalapi.enums.StatusEnum;
import org.example.hospitalapi.exceptions.NotFoundException;
import org.example.hospitalapi.mapper.AppointmentMapper;
import org.example.hospitalapi.repository.AppointmentRepository;
import org.example.hospitalapi.repository.DoctorRepository;
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
class AppointmentServiceImplTest {

  @Mock
  private AppointmentRepository appointmentRepository;

  @Mock
  private DoctorRepository doctorRepository;

  @Mock
  private PatientRepository patientRepository;

  @Mock
  private AppointmentMapper appointmentMapper;

  @InjectMocks
  private AppointmentServiceImpl appointmentService;

  private static final Long APPOINTMENT_ID = 1L;
  private static final Long DOCTOR_ID = 1L;
  private static final Long PATIENT_ID = 1L;
  private static final LocalDate APPOINTMENT_DATE = LocalDate.of(2027, 6, 15);

  private Doctor buildDoctor() {
    return new Doctor(1L, "Pedro", "Cardoso", "12345-SC", "pedro@gmail.com", GenderEnum.MALE, List.of());
  }

  private Patient buildPatient() {
    return new Patient(1L, "Fernanda", "Martins", "fernanda@email.com", GenderEnum.FEMALE, List.of());
  }

  private Appointment buildAppointment() {
    return new Appointment(1L, buildPatient(), buildDoctor(), APPOINTMENT_DATE, StatusEnum.SCHEDULED);
  }

  private AppointmentDto buildAppointmentDto() {
    return new AppointmentDto(1L, 1L, "Fernanda Martins", 1L, "Pedro Cardoso", APPOINTMENT_DATE, StatusEnum.SCHEDULED);
  }

  private CreateAppointmentDto buildCreateDto() {
    return new CreateAppointmentDto(1L, 1L, APPOINTMENT_DATE, StatusEnum.SCHEDULED);
  }

  @Test
  void shouldReturnAppointmentDtoWhenCreateAppointmentSucceeds() {
    CreateAppointmentDto createDto = buildCreateDto();
    Doctor doctor = buildDoctor();
    Patient patient = buildPatient();
    Appointment appointment = buildAppointment();
    AppointmentDto expectedDto = buildAppointmentDto();

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
    when(appointmentMapper.toModel(createDto)).thenReturn(appointment);
    when(appointmentRepository.save(appointment)).thenReturn(appointment);
    when(appointmentMapper.toDto(appointment)).thenReturn(expectedDto);

    AppointmentDto result = appointmentService.createAppointment(createDto);

    assertThat(result).isEqualTo(expectedDto);
    verify(doctorRepository).findById(DOCTOR_ID);
    verify(patientRepository).findById(PATIENT_ID);
    verify(appointmentRepository).save(appointment);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenCreateAppointmentDoctorNotFound() {
    CreateAppointmentDto createDto = buildCreateDto();

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> appointmentService.createAppointment(createDto))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(DOCTOR_ID.toString());
  }

  @Test
  void shouldThrowNotFoundExceptionWhenCreateAppointmentPatientNotFound() {
    CreateAppointmentDto createDto = buildCreateDto();
    Doctor doctor = buildDoctor();

    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
    when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> appointmentService.createAppointment(createDto))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(PATIENT_ID.toString());
  }

  @Test
  void shouldReturnAppointmentDtoWhenFindByIdSucceeds() {
    Appointment appointment = buildAppointment();
    AppointmentDto expectedDto = buildAppointmentDto();

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
    when(appointmentMapper.toDto(appointment)).thenReturn(expectedDto);

    AppointmentDto result = appointmentService.getAppointmentById(APPOINTMENT_ID);

    assertThat(result).isEqualTo(expectedDto);
    verify(appointmentRepository).findById(APPOINTMENT_ID);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenFindByIdNotFound() {
    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> appointmentService.getAppointmentById(APPOINTMENT_ID))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(APPOINTMENT_ID.toString());
  }

  @Test
  void shouldReturnPageOfAppointmentDtoWhenFindAllSucceeds() {
    Appointment appointment = buildAppointment();
    AppointmentDto appointmentDto = buildAppointmentDto();
    Pageable pageable = PageRequest.of(0, 10);
    Page<Appointment> appointmentPage = new PageImpl<>(List.of(appointment), pageable, 1);

    when(appointmentRepository.findAll(pageable)).thenReturn(appointmentPage);
    when(appointmentMapper.toDto(appointment)).thenReturn(appointmentDto);

    Page<AppointmentDto> result = appointmentService.getAppointments(pageable);

    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(appointmentDto);
    verify(appointmentRepository).findAll(pageable);
  }

  @Test
  void shouldReturnFilteredPageWhenFindByDateSucceeds() {
    Appointment appointment = buildAppointment();
    AppointmentDto appointmentDto = buildAppointmentDto();
    Pageable pageable = PageRequest.of(0, 10);
    Page<Appointment> appointmentPage = new PageImpl<>(List.of(appointment), pageable, 1);

    when(appointmentRepository.findByAppointmentDate(APPOINTMENT_DATE, pageable)).thenReturn(appointmentPage);
    when(appointmentMapper.toDto(appointment)).thenReturn(appointmentDto);

    Page<AppointmentDto> result = appointmentService.getAppointmentsByDate(APPOINTMENT_DATE, pageable);

    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(appointmentDto);
    verify(appointmentRepository).findByAppointmentDate(APPOINTMENT_DATE, pageable);
  }

  @Test
  void shouldReturnFilteredPageWhenFindByStatusSucceeds() {
    Appointment appointment = buildAppointment();
    AppointmentDto appointmentDto = buildAppointmentDto();
    Pageable pageable = PageRequest.of(0, 10);
    Page<Appointment> appointmentPage = new PageImpl<>(List.of(appointment), pageable, 1);

    when(appointmentRepository.findByStatus(StatusEnum.SCHEDULED, pageable)).thenReturn(appointmentPage);
    when(appointmentMapper.toDto(appointment)).thenReturn(appointmentDto);

    Page<AppointmentDto> result = appointmentService.getAppointmentsByStatus(StatusEnum.SCHEDULED, pageable);

    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(appointmentDto);
    verify(appointmentRepository).findByStatus(StatusEnum.SCHEDULED, pageable);
  }

  @Test
  void shouldUpdateDateAndStatusWhenPartialUpdateWithoutDoctorOrPatient() {
    Appointment appointment = buildAppointment();
    LocalDate newDate = LocalDate.of(2027, 12, 20);
    UpdateAppointmentDto updateDto = new UpdateAppointmentDto(null, null, newDate, StatusEnum.COMPLETED);
    AppointmentDto updatedDto = new AppointmentDto(
        1L, 1L, "Fernanda Martins", 1L, "Pedro Cardoso", newDate, StatusEnum.COMPLETED);

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
    when(appointmentRepository.save(appointment)).thenReturn(appointment);
    when(appointmentMapper.toDto(appointment)).thenReturn(updatedDto);

    AppointmentDto result = appointmentService.partialUpdateAppointment(APPOINTMENT_ID, updateDto);

    assertThat(result).isEqualTo(updatedDto);
    assertThat(appointment.getAppointmentDate()).isEqualTo(newDate);
    assertThat(appointment.getStatus()).isEqualTo(StatusEnum.COMPLETED);
    verify(appointmentRepository).save(appointment);
  }

  @Test
  void shouldUpdateDoctorWhenPartialUpdateWithDoctorId() {
    Appointment appointment = buildAppointment();
    Doctor newDoctor = new Doctor(2L, "Carlos", "Lima", "99999-SP", "carlos@gmail.com", GenderEnum.MALE, List.of());
    UpdateAppointmentDto updateDto = new UpdateAppointmentDto(null, 2L, null, null);
    AppointmentDto updatedDto = new AppointmentDto(
        1L, 1L, "Fernanda Martins", 2L, "Carlos Lima", APPOINTMENT_DATE, StatusEnum.SCHEDULED);

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
    when(doctorRepository.findById(2L)).thenReturn(Optional.of(newDoctor));
    when(appointmentRepository.save(appointment)).thenReturn(appointment);
    when(appointmentMapper.toDto(appointment)).thenReturn(updatedDto);

    AppointmentDto result = appointmentService.partialUpdateAppointment(APPOINTMENT_ID, updateDto);

    assertThat(result).isEqualTo(updatedDto);
    assertThat(appointment.getDoctor()).isEqualTo(newDoctor);
    verify(doctorRepository).findById(2L);
    verify(appointmentRepository).save(appointment);
  }

  @Test
  void shouldUpdatePatientWhenPartialUpdateWithPatientId() {
    Appointment appointment = buildAppointment();
    Patient newPatient = new Patient(3L, "Mariana", "Costa", "mariana@email.com", GenderEnum.FEMALE, List.of());
    UpdateAppointmentDto updateDto = new UpdateAppointmentDto(3L, null, null, null);
    AppointmentDto updatedDto = new AppointmentDto(
        1L, 3L, "Mariana Costa", 1L, "Pedro Cardoso", APPOINTMENT_DATE, StatusEnum.SCHEDULED);

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
    when(patientRepository.findById(3L)).thenReturn(Optional.of(newPatient));
    when(appointmentRepository.save(appointment)).thenReturn(appointment);
    when(appointmentMapper.toDto(appointment)).thenReturn(updatedDto);

    AppointmentDto result = appointmentService.partialUpdateAppointment(APPOINTMENT_ID, updateDto);

    assertThat(result).isEqualTo(updatedDto);
    assertThat(appointment.getPatient()).isEqualTo(newPatient);
    verify(patientRepository).findById(3L);
    verify(appointmentRepository).save(appointment);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenPartialUpdateAppointmentNotFound() {
    UpdateAppointmentDto updateDto = new UpdateAppointmentDto(null, null, APPOINTMENT_DATE, StatusEnum.COMPLETED);

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> appointmentService.partialUpdateAppointment(APPOINTMENT_ID, updateDto))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(APPOINTMENT_ID.toString());
  }

  @Test
  void shouldCallDeleteByIdWhenDeleteAppointmentSucceeds() {
    Appointment appointment = buildAppointment();

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));

    appointmentService.deleteAppointment(APPOINTMENT_ID);

    verify(appointmentRepository).deleteById(APPOINTMENT_ID);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenDeleteAppointmentNotFound() {
    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> appointmentService.deleteAppointment(APPOINTMENT_ID))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(APPOINTMENT_ID.toString());
  }
}
