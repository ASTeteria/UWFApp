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
import wushu.dto.ContactCompetitionApplicationDTO;
import wushu.dto.PagedResponseDTO;
import wushu.service.ContactCompetitionApplicationService;

@RestController
@RequestMapping("/api/contact-competition-applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ContactCompetitionApplicationController {
    private final ContactCompetitionApplicationService contactCompetitionApplicationService;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<PagedResponseDTO<ContactCompetitionApplicationDTO>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<ContactCompetitionApplicationDTO> applications = contactCompetitionApplicationService.getAllApplications(pageable, search);
        return ResponseEntity.ok(new PagedResponseDTO<>(applications));
    }

    @GetMapping("/my")
    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<PagedResponseDTO<ContactCompetitionApplicationDTO>> getMyApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<ContactCompetitionApplicationDTO> applications = contactCompetitionApplicationService.getApplicationsByCurrentUser(pageable, search);
        return ResponseEntity.ok(new PagedResponseDTO<>(applications));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_SUPERADMIN", "ROLE_USER"})
    public ResponseEntity<ContactCompetitionApplicationDTO> getApplication(@PathVariable Long id) {
        return ResponseEntity.ok(contactCompetitionApplicationService.getApplicationById(id));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ContactCompetitionApplicationDTO> createApplication(@Valid @RequestBody ContactCompetitionApplicationDTO applicationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactCompetitionApplicationService.createApplication(applicationDTO));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ContactCompetitionApplicationDTO> updateApplication(@PathVariable Long id, @Valid @RequestBody ContactCompetitionApplicationDTO applicationDTO) {
        return ResponseEntity.ok(contactCompetitionApplicationService.updateApplication(id, applicationDTO));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        contactCompetitionApplicationService.deleteApplication(id);
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