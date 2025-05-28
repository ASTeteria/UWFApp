package wushu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wushu.entity.ContactCompetitionApplication;

import java.util.List;

@Repository
public interface ContactCompetitionApplicationRepository extends JpaRepository<ContactCompetitionApplication, Long> {
    List<ContactCompetitionApplication> findByUserId(Long userId);

    Page<ContactCompetitionApplication> findByUserId(Long userId, Pageable pageable);

    Page<ContactCompetitionApplication> findByAthleteFirstNameContainingIgnoreCaseOrAthleteLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);
}