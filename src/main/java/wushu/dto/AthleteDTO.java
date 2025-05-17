package wushu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import wushu.entity.Athlete;
import wushu.entity.ProgramType;

import java.util.Date;

public record AthleteDTO(
        Long id,

        @NotBlank(message = "Athlete first name is required")
        String firstName,

        @NotBlank(message = "Athlete last name is required")
        String lastName,

        @NotNull(message = "Birth date is required")
        Date birthDate,

        @NotNull(message = "Program is required")
        ProgramType programType,

        Long userId
) {}
