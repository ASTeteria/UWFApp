package wushu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Data
@Entity
public class CompetitionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String competitionName;
    private String athleteFirstName;
    private String athleteLastName;
    @Temporal(TemporalType.DATE) // Вказуємо, що тип даних - DATE
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private CompetitionAgeCategory ageCategory;

    @Enumerated(EnumType.STRING)
    private WeaponlessProgram weaponlessProgram;

    @Enumerated(EnumType.STRING)
    private ShortWeaponProgram shortWeaponProgram;

    @Enumerated(EnumType.STRING)
    private LongWeaponProgram longWeaponProgram;

    private String duilian;

    private Long userId;
}
