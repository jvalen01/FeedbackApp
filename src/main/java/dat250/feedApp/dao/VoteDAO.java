package dat250.feedApp.dao;

import dat250.feedApp.Vote;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDAO extends AbstractDAO<Vote, Long> {

    @Autowired
    public VoteDAO(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Vote> getEntityClass() {
        return Vote.class;
    }
}
