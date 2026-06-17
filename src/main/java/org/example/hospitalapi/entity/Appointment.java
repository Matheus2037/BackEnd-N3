package org.example.hospitalapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hospitalapi.enums.StatusEnum;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "doctor_id", nullable = false)
  private Doctor doctor;

  @Column(nullable = false)
  private LocalDate appointmentDate;

  @Column(nullable = false)
  private StatusEnum status;
}
