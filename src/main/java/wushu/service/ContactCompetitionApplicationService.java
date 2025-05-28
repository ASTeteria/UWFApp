package wushu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wushu.dto.ContactCompetitionApplicationDTO;
import wushu.entity.ContactCompetitionApplication;
import wushu.exception.NotFoundException;
import wushu.mapper.ContactCompetitionApplicationMapper;
import wushu.repository.ContactCompetitionApplicationRepository;

@Service
@RequiredArgsConstructor
public class ContactCompetitionApplicationService {
    private final ContactCompetitionApplicationRepository contactCompetitionApplicationRepository;
    private final ContactCompetitionApplicationMapper contactCompetitionApplicationMapper;
    private final AuthService authService;

    public Page<ContactCompetitionApplicationDTO> getAllApplications(Pageable pageable, String search) {
        Page<ContactCompetitionApplication> applications;
        if (search != null && !search.isEmpty()) {
            applications = contactCompetitionApplicationRepository.findByAthleteFirstNameContainingIgnoreCaseOrAthleteLastNameContainingIgnoreCase(
                    search, search, pageable);
        } else {
            applications = contactCompetitionApplicationRepository.findAll(pageable);
        }
        return applications.map(contactCompetitionApplicationMapper::toDto);
    }

    public Page<ContactCompetitionApplicationDTO> getApplicationsByCurrentUser(Pageable pageable, String search) {
        Long userId = getCurrentUserId();
        Page<ContactCompetitionApplication> applications;
        if (search != null && !search.isEmpty()) {
            applications = contactCompetitionApplicationRepository.findByUserId(userId, pageable);
            applications = (Page<ContactCompetitionApplication>) applications.filter(a -> a.getAthleteFirstName().toLowerCase().contains(search.toLowerCase()) ||
                    a.getAthleteLastName().toLowerCase().contains(search.toLowerCase()));
        } else {
            applications = contactCompetitionApplicationRepository.findByUserId(userId, pageable);
        }
        return applications.map(contactCompetitionApplicationMapper::toDto);
    }

    public ContactCompetitionApplicationDTO getApplicationById(Long id) {
        ContactCompetitionApplication application = contactCompetitionApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application not found"));
        Long userId = getCurrentUserId();
        if (!application.getUserId().equals(userId)) {
            throw new NotFoundException("Application not found");
        }
        return contactCompetitionApplicationMapper.toDto(application);
    }

    public ContactCompetitionApplicationDTO createApplication(ContactCompetitionApplicationDTO applicationDTO) {
        ContactCompetitionApplication application = contactCompetitionApplicationMapper.toEntity(applicationDTO);
        Long userId = getCurrentUserId();
        application.setUserId(userId);
        ContactCompetitionApplication savedApplication = contactCompetitionApplicationRepository.save(application);
        return contactCompetitionApplicationMapper.toDto(savedApplication);
    }

    public ContactCompetitionApplicationDTO updateApplication(Long id, ContactCompetitionApplicationDTO applicationDTO) {
        ContactCompetitionApplication existingApplication = contactCompetitionApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competition application not found with id: " + id));
        Long userId = getCurrentUserId();
        if (!existingApplication.getUserId().equals(userId)) {
            throw new NotFoundException("Application not found");
        }
        existingApplication.setCompetitionName(applicationDTO.competitionName());
        existingApplication.setAthleteFirstName(applicationDTO.athleteFirstName());
        existingApplication.setAthleteLastName(applicationDTO.athleteLastName());
        existingApplication.setBirthDate(applicationDTO.birthDate());
        existingApplication.setGender(applicationDTO.gender());
        existingApplication.setAgeCategory(applicationDTO.ageCategory());
        existingApplication.setContactProgram(applicationDTO.contactProgram());
        existingApplication.setWeightCategory(applicationDTO.weightCategory());
        ContactCompetitionApplication updatedApplication = contactCompetitionApplicationRepository.save(existingApplication);
        return contactCompetitionApplicationMapper.toDto(updatedApplication);
    }

    public void deleteApplication(Long id) {
        ContactCompetitionApplication application = contactCompetitionApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CompetitionApplication not found with id: " + id));
        Long userId = getCurrentUserId();
        if (!application.getUserId().equals(userId)) {
            throw new NotFoundException("Application not found");
        }
        contactCompetitionApplicationRepository.deleteById(id);
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return authService.getUserByUsername(username).getId();
    }
}