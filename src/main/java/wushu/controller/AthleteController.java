//package wushu.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import wushu.dto.AthleteDTO;
//import wushu.entity.Athlete;
//import wushu.service.AthleteService.java;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/athletes")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
//public class AthleteController {
//    private final AthleteService.java athleteService;
//
//
//    @GetMapping
//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
//    public ResponseEntity<List<AthleteDTO>> getAllAthletes() {
//
//        return ResponseEntity.ok(athleteService.getAllAthletes());
//    }
//
//    @GetMapping("/{id}")
//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
//    public ResponseEntity<AthleteDTO> getAthleteById(@PathVariable Long id) {
//
//        return ResponseEntity.ok(athleteService.getAthleteById(id));
//    }
//
//    @PostMapping
//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    public ResponseEntity<AthleteDTO> createAthlete(@Valid @RequestBody AthleteDTO athleteDTO) {
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.createAthlete(athleteDTO));
//    }
//
//    @PutMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<AthleteDTO> updateAthlete(@PathVariable Long id, @Valid @RequestBody AthleteDTO athleteDTO) {
//        return ResponseEntity.ok(athleteService.updateAthlete(id, athleteDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
//
//        athleteService.deleteAthlete(id);
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
import wushu.dto.AthleteDTO;
import wushu.service.AthleteService;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AthleteController {
    private final AthleteService athleteService;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<List<AthleteDTO>> getAllAthletes() {
        return ResponseEntity.ok(athleteService.getAllAthletes());
    }

    @GetMapping("/my")
    @Secured("ROLE_USER")
    public ResponseEntity<List<AthleteDTO>> getMyAthletes() {
        return ResponseEntity.ok(athleteService.getAthletesByCurrentUser());
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
}
