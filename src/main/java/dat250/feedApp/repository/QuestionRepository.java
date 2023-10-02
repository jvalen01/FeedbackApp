package dat250.feedApp.repository;

import dat250.feedApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Any additional custom methods, if required, can be defined here
}
