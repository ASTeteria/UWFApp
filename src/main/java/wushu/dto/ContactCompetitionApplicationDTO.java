package wushu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import wushu.entity.*;

import java.util.Date;


public record ContactCompetitionApplicationDTO(
        Long id,
        @NotBlank(message = "Competition name is required")
        String competitionName,

        @NotBlank(message = "Athlete first name is required")
        String athleteFirstName,

        @NotBlank(message = "Athlete last name is required")
        String athleteLastName,

        @NotNull(message = "Birth date is required")
        Date birthDate,

        @NotNull(message = "Gender is required")
        Gender gender,


        @NotNull(message = "Age category is required")
        ContactAgeCategory ageCategory,
        @NotNull(message = "Contact Program category is required")
        ContactProgram contactProgram,
        @NotNull(message = "Weight category category is required")
        WeightCategory weightCategory,
        Long userId
) {}
