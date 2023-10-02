package dat250.feedApp;

import dat250.feedApp.dao.*;
import dat250.feedApp.driver.DataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FeedAppTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private DataInitializer initializer;

    @BeforeEach
    public void setUp() {
        initializer.init(); // this initializes data into database
    }

    @Test
    public void testDomainModelPersistence() {
        // Load user
        User user = em.find(User.class, 1L);

        // Test person data
        assertThat(user.getUsername()).isEqualTo("Bob");
        assertThat(user.getPassword()).isEqualTo("123456789");

        // Test address
        assertThat(user.getVotes().size()).isEqualTo(1);
        Vote vote = user.getVotes().iterator().next();

        assertThat(vote.getAnswer()).isEqualTo(true);
        assertThat(vote.getQuestion().getQuestion()).isEqualTo("What is this?");
        Question question = vote.getQuestion();
        assertThat(question.getPoll().getName()).isEqualTo("TestPoll");
    }
}
