//package wushu.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import wushu.dto.ContactCompetitionApplicationDTO;
//import wushu.entity.ContactCompetitionApplication;
//import wushu.service.ContactCompetitionApplictionService;
//
//import java.util.List;
//@RestController
//@RequestMapping("/api/contact-competition-applications")
//@RequiredArgsConstructor
//public class ContactCompetitionApplicationController {
//    private final ContactCompetitionApplictionService contactCompetitionApplicationService;
//
//    @GetMapping
//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
//    public ResponseEntity<List<ContactCompetitionApplicationDTO>> getAllApplications() {
//        return ResponseEntity.ok(contactCompetitionApplicationService.getAllApplications());
//    }
//
//    @GetMapping("/{id}")
//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
//    public ResponseEntity<ContactCompetitionApplicationDTO> getApplication(@PathVariable Long id) {
//        return ResponseEntity.ok(contactCompetitionApplicationService.getApplicationById(id));
//    }
//
//    @PostMapping
//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    public ResponseEntity<ContactCompetitionApplicationDTO> createApplication(@Valid @RequestBody ContactCompetitionApplicationDTO applicationDTO) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(contactCompetitionApplicationService.createApplication(applicationDTO));
//    }
//
//    @PutMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<ContactCompetitionApplicationDTO> updateApplication(@PathVariable Long id, @Valid @RequestBody ContactCompetitionApplicationDTO applicationDTO) {
//        return ResponseEntity.ok(contactCompetitionApplicationService.updateApplication(id, applicationDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
//
//        contactCompetitionApplicationService.deleteApplication(id);
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
import wushu.dto.ContactCompetitionApplicationDTO;
import wushu.service.ContactCompetitionApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/contact-competition-applications")
@RequiredArgsConstructor
public class ContactCompetitionApplicationController {
    private final ContactCompetitionApplicationService contactCompetitionApplicationService;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<List<ContactCompetitionApplicationDTO>> getAllApplications() {
        return ResponseEntity.ok(contactCompetitionApplicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
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
}