//package wushu.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import wushu.entity.CompetitionApplication;
//
//@Repository
//public interface CompetitionApplicationRepository extends JpaRepository<CompetitionApplication, Long> {
//}


package wushu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wushu.entity.CompetitionApplication;

import java.util.List;

@Repository
public interface CompetitionApplicationRepository extends JpaRepository<CompetitionApplication, Long> {
    List<CompetitionApplication> findByUserId(Long userId);
}
