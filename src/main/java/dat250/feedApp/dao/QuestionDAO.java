package dat250.feedApp.dao;

import dat250.feedApp.Question;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDAO extends AbstractDAO<Question, Long> {

    @Autowired
    public QuestionDAO(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Question> getEntityClass() {
        return Question.class;
    }
}