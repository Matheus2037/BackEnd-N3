package org.example.hospitalapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hospitalapi.enums.GenderEnum;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column
  private String email;
  @Column(nullable = false)
  private GenderEnum gender;

  @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
  private List<Appointment> appointments;
}
