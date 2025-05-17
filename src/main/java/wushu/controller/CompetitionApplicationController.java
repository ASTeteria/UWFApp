//package wushu.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import wushu.dto.CompetitionApplicationDTO;
//import wushu.entity.CompetitionApplication;
//import wushu.service.CompetitionApplicationService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/competition-applications")
//@RequiredArgsConstructor
//public class CompetitionApplicationController {
//    private final CompetitionApplicationService competitionApplicationService;
//
//
//    @GetMapping
//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
//    public ResponseEntity<List<CompetitionApplicationDTO>> getAllApplications() {
//        return ResponseEntity.ok(competitionApplicationService.getAllApplications());
//    }
//
//    @GetMapping("/{id}")
//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
//    public ResponseEntity<CompetitionApplicationDTO> getApplication(@PathVariable Long id) {
//        return ResponseEntity.ok(competitionApplicationService.getApplicationById(id));
//    }
//
//    @PostMapping
//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    public ResponseEntity<CompetitionApplicationDTO> createApplication(@Valid @RequestBody CompetitionApplicationDTO applicationDTO) {
//        return ResponseEntity.status(HttpStatus.CREATED).body (competitionApplicationService.createApplication(applicationDTO));
//    }
//
//    @PutMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<CompetitionApplicationDTO> updateApplication(@PathVariable Long id, @Valid @RequestBody CompetitionApplicationDTO applicationDTO) {
//        return ResponseEntity.ok(competitionApplicationService.updateApplication(id, applicationDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
//
//        competitionApplicationService.deleteApplication(id);
//        return ResponseEntity.noContent().build();
//    }
//}

package wushu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import wushu.dto.CompetitionApplicationDTO;
import wushu.service.CompetitionApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/competition-applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class CompetitionApplicationController {
    private final CompetitionApplicationService competitionApplicationService;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<List<CompetitionApplicationDTO>> getAllApplications() {
        return ResponseEntity.ok(competitionApplicationService.getAllApplications());
    }

    @GetMapping("/my")
    @Secured("ROLE_USER")
    public ResponseEntity<List<CompetitionApplicationDTO>> getMyApplications() {
        return ResponseEntity.ok(competitionApplicationService.getApplicationsByCurrentUser());
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
}
