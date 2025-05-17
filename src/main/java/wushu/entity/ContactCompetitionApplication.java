package wushu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class ContactCompetitionApplication {
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

    private Long userId;

    @Enumerated(EnumType.STRING)
    private ContactAgeCategory ageCategory;

    @Enumerated(EnumType.STRING)
    private ContactProgram contactProgram;

    @Enumerated(EnumType.STRING)
    private WeightCategory weightCategory;

}
