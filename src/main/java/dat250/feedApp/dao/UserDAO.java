package dat250.feedApp.dao;

import dat250.feedApp.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractDAO<User, Long> {

    @Autowired
    public UserDAO(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
