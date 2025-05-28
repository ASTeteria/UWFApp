package wushu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wushu.entity.CompetitionApplication;

import java.util.List;

@Repository
public interface CompetitionApplicationRepository extends JpaRepository<CompetitionApplication, Long> {
    List<CompetitionApplication> findByUserId(Long userId);

    Page<CompetitionApplication> findByUserId(Long userId, Pageable pageable);

    Page<CompetitionApplication> findByAthleteFirstNameContainingIgnoreCaseOrAthleteLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);
}