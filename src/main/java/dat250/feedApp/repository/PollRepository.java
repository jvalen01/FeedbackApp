package dat250.feedApp.repository;

import dat250.feedApp.model.Poll;
import dat250.feedApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    // Any additional custom methods, if required, can be defined here
    Optional<Poll> findByCode(String code);

    List<Poll> findByUser(User user);  // <-- Add this method

}

