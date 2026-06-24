package org.example.hospitalapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.hospitalapi.enums.GenderEnum;

public record UpdateDoctorDto(
    String firstName,
    String lastName,
    String email,
    @Schema(example = "MALE")
    GenderEnum gender
) {

}
