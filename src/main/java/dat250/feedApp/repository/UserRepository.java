package dat250.feedApp.repository;

import dat250.feedApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Any additional custom methods, if required, can be defined here
}