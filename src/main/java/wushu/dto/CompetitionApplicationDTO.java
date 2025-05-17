package wushu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import wushu.entity.*;

import java.util.Date;


public record CompetitionApplicationDTO(
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
        CompetitionAgeCategory ageCategory,

        WeaponlessProgram weaponlessProgram,
        ShortWeaponProgram shortWeaponProgram,
        LongWeaponProgram longWeaponProgram,
        String duilian,
        Long userId
) {}
