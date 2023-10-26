package dat250.feedApp.repository;

import dat250.feedApp.model.User;
import dat250.feedApp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Any additional custom methods, if required, can be defined here
}

