package wushu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wushu.dto.AthleteDTO;
import wushu.entity.Athlete;
import wushu.exception.NotFoundException;
import wushu.mapper.AthleteMapper;
import wushu.repository.AthleteRepository;

@Service
@RequiredArgsConstructor
public class AthleteService {
    private final AthleteRepository athleteRepository;
    private final AthleteMapper athleteMapper;
    private final AuthService authService;

    public Page<AthleteDTO> getAllAthletes(Pageable pageable, String search) {
        Page<Athlete> athletes;
        if (search != null && !search.isEmpty()) {
            athletes = athleteRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                    search, search, pageable);
        } else {
            athletes = athleteRepository.findAll(pageable);
        }
        return athletes.map(athleteMapper::toDto);
    }

    public Page<AthleteDTO> getAthletesByCurrentUser(Pageable pageable, String search) {
        Long userId = getCurrentUserId();
        Page<Athlete> athletes;
        if (search != null && !search.isEmpty()) {
            // Для пошуку за userId потрібно кастомний запит, тут спрощуємо
            athletes = athleteRepository.findByUserId(userId, pageable);
            athletes = (Page<Athlete>) athletes.filter(a -> a.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                    a.getLastName().toLowerCase().contains(search.toLowerCase()));
        } else {
            athletes = athleteRepository.findByUserId(userId, pageable);
        }
        return athletes.map(athleteMapper::toDto);
    }

    public AthleteDTO getAthleteById(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Athlete not found"));
        return athleteMapper.toDto(athlete);
    }

    public AthleteDTO createAthlete(AthleteDTO athleteDTO) {
        Athlete athlete = athleteMapper.toEntity(athleteDTO);
        Long userId = getCurrentUserId();
        athlete.setUserId(userId);
        Athlete savedAthlete = athleteRepository.save(athlete);
        return athleteMapper.toDto(savedAthlete);
    }

    public AthleteDTO updateAthlete(Long id, AthleteDTO athleteDTO) {
        Athlete existingAthlete = athleteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Athlete not found with id: " + id));
        if (!isCurrentUserOwner(existingAthlete)) {
            throw new NotFoundException("Athlete is owned by another user");
        }
        existingAthlete.setFirstName(athleteDTO.firstName());
        existingAthlete.setLastName(athleteDTO.lastName());
        existingAthlete.setBirthDate(athleteDTO.birthDate());
        existingAthlete.setProgramType(athleteDTO.programType());
        Athlete updatedAthlete = athleteRepository.save(existingAthlete);
        return athleteMapper.toDto(updatedAthlete);
    }

    public void deleteAthlete(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Athlete not found with id: " + id));
        if (!isCurrentUserOwner(athlete)) {
            throw new NotFoundException("Athlete is owned by another user");
        }
        athleteRepository.deleteById(id);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return authService.getUserByUsername(username).getId();
    }

    private boolean isCurrentUserOwner(Athlete athlete) {
        return athlete.getUserId().equals(getCurrentUserId());
    }
}