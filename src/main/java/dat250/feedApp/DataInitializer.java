package dat250.feedApp;


import dat250.feedApp.model.Poll;
import dat250.feedApp.model.Question;
import dat250.feedApp.model.User;
import dat250.feedApp.model.Vote;
import dat250.feedApp.repository.PollRepository;
import dat250.feedApp.repository.QuestionRepository;
import dat250.feedApp.repository.UserRepository;
import dat250.feedApp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @PostConstruct
    @Transactional
    public void initData() {
        User user = new User();
        user.setUsername("Bob");
        user.setPassword("123456789");
        user.setAdminRights(false);

        Poll poll = new Poll();
        poll.setName("TestPoll");
        poll.setActive(true);
        poll.setAccessMode("public");

        // Link poll to user using helper method
        user.addPoll(poll); // This sets the user in the poll and adds the poll to the user's list

        Question question = new Question();
        question.setQuestion("What is this?");
        question.setNoVotes(0);
        question.setTotalVotes(1);
        question.setYesVotes(1);

        // Link question to poll using helper method
        poll.addQuestion(question); // This sets the poll in the question and adds the question to the poll's list

        Vote vote = new Vote();
        vote.setAnswer(true);

        // Link vote to question and user
        question.addVote(vote); // This sets the question in the vote and adds the vote to the question's list
        user.addVote(vote); // This sets the user in the vote and adds the vote to the user's list

        // Now, persist the entities. The order matters due to the relationships:

        // User is a top-level entity here
        userRepository.save(user);

        // Poll is linked to user
        pollRepository.save(poll);

        // Question is linked to poll
        questionRepository.save(question);

        // Vote is linked to both question and user
        voteRepository.save(vote);
    }

}


