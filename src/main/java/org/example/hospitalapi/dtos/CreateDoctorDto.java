package org.example.hospitalapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.hospitalapi.enums.GenderEnum;

public record CreateDoctorDto(

        @Size(
                min = 3,
                max = 256,
                message = "O campo firstName deve ter entre 3 a 256 caracteres"
        )
        String firstName,
        @Size(
                min = 3,
                max = 256,
                message = "O campo lastName deve ter entre 3 a 256 caracteres"
        )
        String lastName,
        @Size(
                min = 3,
                max = 10,
                message = "O campo registration deve conter entre 3 a 10 caracteres"
        )
        String registration,
        @Email
        @Size(
                min = 4,
                max = 256,
                message = "O campo email deve ter entre 5 a 256 caracteres"
        )
        String email,
        @NotNull(
                message = "O campo gender deve ser uma das seguintes opções: (Male,Female,Other)"
        )
        GenderEnum gender
) {}