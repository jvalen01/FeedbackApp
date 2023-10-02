package dat250.feedApp.driver;

import dat250.feedApp.*;
import dat250.feedApp.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private UserDAO userdao;

    @Autowired
    private PollDAO polldao;

    @Autowired
    private QuestionDAO questiondao;

    @Autowired
    private VoteDAO votedao;

    @PostConstruct
    @Transactional
    public void init() {
        createObjects();
    }

    private void createObjects() {
        User user = new User();
        user.setUsername("Bob");
        user.setPassword("123456789");
        user.setAdminRights(false);
        userdao.persist(user);

        Poll poll = new Poll();
        poll.setName("TestPoll");
        poll.setActive(true);
        poll.setAccessMode("public");
        poll.setUser(user);
        user.getPolls().add(poll);
        polldao.persist(poll);

        Question question = new Question();
        question.setQuestion("What is this?");
        question.setNoVotes(0);
        question.setTotalVotes(1);
        question.setYesVotes(1);
        question.setPoll(poll);
        poll.getQuestions().add(question);
        questiondao.persist(question);

        Vote vote = new Vote();
        vote.setAnswer(true);
        vote.setQuestion(question);
        question.getVotes().add(vote);
        vote.setUser(user);
        user.getVotes().add(vote);
        votedao.persist(vote);
    }
}

