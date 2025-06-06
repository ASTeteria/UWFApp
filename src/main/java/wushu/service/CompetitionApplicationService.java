package wushu.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wushu.dto.CompetitionApplicationDTO;
import wushu.entity.CompetitionApplication;
import wushu.entity.Role;
import wushu.entity.User;
import wushu.exception.NotFoundException;
import wushu.mapper.CompetitionApplicationMapper;
import wushu.repository.CompetitionApplicationRepository;
import wushu.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionApplicationService {
    private final CompetitionApplicationRepository competitionApplicationRepository;
    private final CompetitionApplicationMapper competitionApplicationMapper;
    private final AuthService authService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public Page<CompetitionApplicationDTO> getAllApplications(Pageable pageable, String search) {
        Page<CompetitionApplication> applications;
        if (search != null && !search.isEmpty()) {
            applications = competitionApplicationRepository.findByAthleteFirstNameContainingIgnoreCaseOrAthleteLastNameContainingIgnoreCase(
                    search, search, pageable);
        } else {
            applications = competitionApplicationRepository.findAll(pageable);
        }
        return applications.map(competitionApplicationMapper::toDto);
    }

    public Page<CompetitionApplicationDTO> getApplicationsByCurrentUser(Pageable pageable, String search) {
        Long userId = getCurrentUserId();
        Page<CompetitionApplication> applications;
        if (search != null && !search.isEmpty()) {
            applications = competitionApplicationRepository.findByUserId(userId, pageable);
            applications = (Page<CompetitionApplication>) applications.filter(a -> a.getAthleteFirstName().toLowerCase().contains(search.toLowerCase()) ||
                    a.getAthleteLastName().toLowerCase().contains(search.toLowerCase()));
        } else {
            applications = competitionApplicationRepository.findByUserId(userId, pageable);
        }
        return applications.map(competitionApplicationMapper::toDto);
    }

    public CompetitionApplicationDTO getApplicationById(Long id) {
        CompetitionApplication application = competitionApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CompetitionApplication not found"));
        return competitionApplicationMapper.toDto(application);
    }

//    public CompetitionApplicationDTO createApplication(CompetitionApplicationDTO applicationDTO) {
//        CompetitionApplication application = competitionApplicationMapper.toEntity(applicationDTO);
//        Long userId = getCurrentUserId();
//        application.setUserId(userId);
//        CompetitionApplication savedApplication = competitionApplicationRepository.save(application);
//        return competitionApplicationMapper.toDto(savedApplication);
//    }
public CompetitionApplicationDTO createApplication(CompetitionApplicationDTO applicationDTO) {
    CompetitionApplication application = competitionApplicationMapper.toEntity(applicationDTO);
    Long userId = getCurrentUserId();
    application.setUserId(userId);
    CompetitionApplication savedApplication = competitionApplicationRepository.save(application);

    // Відправка email модераторам
    notifyModerators(savedApplication);

    return competitionApplicationMapper.toDto(savedApplication);
}

    public CompetitionApplicationDTO updateApplication(Long id, CompetitionApplicationDTO applicationDTO) {
        CompetitionApplication existingApplication = competitionApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competition application not found with id: " + id));
        if (!isCurrentUserOwner(existingApplication)) {
            throw new NotFoundException("Application is owned by another user");
        }
        existingApplication.setCompetitionName(applicationDTO.competitionName());
        existingApplication.setAthleteFirstName(applicationDTO.athleteFirstName());
        existingApplication.setAthleteLastName(applicationDTO.athleteLastName());
        existingApplication.setBirthDate(applicationDTO.birthDate());
        existingApplication.setGender(applicationDTO.gender());
        existingApplication.setAgeCategory(applicationDTO.ageCategory());
        existingApplication.setWeaponlessProgram(applicationDTO.weaponlessProgram());
        existingApplication.setShortWeaponProgram(applicationDTO.shortWeaponProgram());
        existingApplication.setLongWeaponProgram(applicationDTO.longWeaponProgram());
        existingApplication.setDuilian(applicationDTO.duilian());
        CompetitionApplication updatedApplication = competitionApplicationRepository.save(existingApplication);
        return competitionApplicationMapper.toDto(updatedApplication);
    }

    public void deleteApplication(Long id) {
        CompetitionApplication application = competitionApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CompetitionApplication not found with id: " + id));
        if (!isCurrentUserOwner(application)) {
            throw new NotFoundException("Application is owned by another user");
        }
        competitionApplicationRepository.deleteById(id);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return authService.getUserByUsername(username).getId();
    }

    private boolean isCurrentUserOwner(CompetitionApplication application) {
        return application.getUserId().equals(getCurrentUserId());
    }

    private void notifyModerators(CompetitionApplication application) {
        List<User> moderators = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(Role.MODERATOR))
                .toList();

        String subject = "Нова заявка на змагання";
        String text = String.format(
                "Нова заявка на змагання створена:<br>" +
                        "Назва змагання: %s<br>" +
                        "Ім'я спортсмена: %s %s<br>" +
                        "Дата народження: %s<br>" +
                        "Категорія: %s",
                application.getCompetitionName(),
                application.getAthleteFirstName(),
                application.getAthleteLastName(),
                application.getBirthDate(),
                application.getAgeCategory().getDisplayName()
        );

        for (User moderator : moderators) {
            try {
                emailService.sendEmail(moderator.getEmail(), subject, text); // Зміна з username на email
            } catch (MessagingException e) {
                System.err.println("Не вдалося надіслати email модератору " + moderator.getUsername() + ": " + e.getMessage());
            }
        }
    }
}
