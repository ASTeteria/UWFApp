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
import wushu.dto.CompetitionApplicationDTO;
import wushu.dto.PagedResponseDTO;
import wushu.service.CompetitionApplicationService;

@RestController
@RequestMapping("/api/competition-applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class CompetitionApplicationController {
    private final CompetitionApplicationService competitionApplicationService;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<PagedResponseDTO<CompetitionApplicationDTO>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<CompetitionApplicationDTO> applications = competitionApplicationService.getAllApplications(pageable, search);
        return ResponseEntity.ok(new PagedResponseDTO<>(applications));
    }

    @GetMapping("/my")
    @Secured("ROLE_USER")
    public ResponseEntity<PagedResponseDTO<CompetitionApplicationDTO>> getMyApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<CompetitionApplicationDTO> applications = competitionApplicationService.getApplicationsByCurrentUser(pageable, search);
        return ResponseEntity.ok(new PagedResponseDTO<>(applications));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<CompetitionApplicationDTO> getApplication(@PathVariable Long id) {
        return ResponseEntity.ok(competitionApplicationService.getApplicationById(id));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<CompetitionApplicationDTO> createApplication(@Valid @RequestBody CompetitionApplicationDTO applicationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competitionApplicationService.createApplication(applicationDTO));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<CompetitionApplicationDTO> updateApplication(@PathVariable Long id, @Valid @RequestBody CompetitionApplicationDTO applicationDTO) {
        return ResponseEntity.ok(competitionApplicationService.updateApplication(id, applicationDTO));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        competitionApplicationService.deleteApplication(id);
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