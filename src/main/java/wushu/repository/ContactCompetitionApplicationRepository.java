//package wushu.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import wushu.entity.ContactCompetitionApplication;
//
//@Repository
//public interface ContactCompetitionApplicationRepository extends JpaRepository<ContactCompetitionApplication, Long> {
//}

package wushu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wushu.entity.ContactCompetitionApplication;

import java.util.List;

@Repository
public interface ContactCompetitionApplicationRepository extends JpaRepository<ContactCompetitionApplication, Long> {
    List<ContactCompetitionApplication> findByUserId(Long userId);
}
