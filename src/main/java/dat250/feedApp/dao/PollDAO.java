package dat250.feedApp.dao;

import dat250.feedApp.Poll;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PollDAO extends AbstractDAO<Poll, Long> {

    @Autowired
    public PollDAO(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Poll> getEntityClass() {
        return Poll.class;
    }
}
