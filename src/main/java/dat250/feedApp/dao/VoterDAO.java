package dat250.feedApp.dao;

import dat250.feedApp.Voter;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoterDAO extends AbstractDAO<Voter, Long> {

    @Autowired
    public VoterDAO(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Voter> getEntityClass() {
        return Voter.class;
    }
}
