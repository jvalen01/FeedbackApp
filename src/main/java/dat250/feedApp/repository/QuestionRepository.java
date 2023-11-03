package dat250.feedApp.repository;

import dat250.feedApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<List<Question>> findByPollId(Long id);
    // Any additional custom methods, if required, can be defined here
}
