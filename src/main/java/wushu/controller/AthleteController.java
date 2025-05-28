package wushu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import wushu.dto.AthleteDTO;
import wushu.dto.PagedResponseDTO;
import wushu.service.AthleteService;

@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AthleteController {
    private final AthleteService athleteService;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<PagedResponseDTO<AthleteDTO>> getAllAthletes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<AthleteDTO> athletes = athleteService.getAllAthletes(pageable, search);
        return ResponseEntity.ok(new PagedResponseDTO<>(athletes));
    }

    @GetMapping("/my")
    @Secured("ROLE_USER")
    public ResponseEntity<PagedResponseDTO<AthleteDTO>> getMyAthletes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<AthleteDTO> athletes = athleteService.getAthletesByCurrentUser(pageable, search);
        return ResponseEntity.ok(new PagedResponseDTO<>(athletes));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<AthleteDTO> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<AthleteDTO> createAthlete(@Valid @RequestBody AthleteDTO athleteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.createAthlete(athleteDTO));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<AthleteDTO> updateAthlete(@PathVariable Long id, @Valid @RequestBody AthleteDTO athleteDTO) {
        return ResponseEntity.ok(athleteService.updateAthlete(id, athleteDTO));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.noContent().build();
    }

    private Sort parseSort(String sort) {
        String[] parts = sort.split(",");
        String property = parts[0];
        Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, property);
    }
}