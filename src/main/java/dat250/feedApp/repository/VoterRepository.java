package dat250.feedApp.repository;

import dat250.feedApp.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {
    // Any additional custom methods, if required, can be defined here
}
