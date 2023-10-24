package dat250.feedApp.repository;

import dat250.feedApp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    // Any additional custom methods, if required, can be defined here
    Optional<Poll> findByCode(String code);

}

