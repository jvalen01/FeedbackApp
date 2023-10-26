package dat250.feedApp.service;

import dat250.feedApp.model.Question;
import dat250.feedApp.model.Vote;
import dat250.feedApp.model.User;
import dat250.feedApp.repository.QuestionRepository;
import dat250.feedApp.repository.UserRepository;
import dat250.feedApp.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private QuestionRepository questionRepository;  // Autowire the QuestionRepository

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);


    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }

    public Optional<Vote> findVoteById(Long id) {
        return voteRepository.findById(id);
    }

    public Vote saveVote(Vote vote) {
        // Fetch the User entity from the database using firebaseUID
        String username = vote.getUser().getUsername();
        logger.info("FirebaseUID: " + username);
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with firebaseUID: " + username));

        // Associate the user with the vote
        vote.setUser(existingUser);
        logger.info("Before adding vote: " + existingUser + ", Votes: " + existingUser.getVotes().size());
        existingUser.addVote(vote);
        logger.info("After adding vote: " + existingUser + ", Votes: " + existingUser.getVotes().size());
        userRepository.save(existingUser);



        // Fetch the current state of the Question entity from the database
        Question existingQuestion = questionRepository.findById(vote.getQuestion().getId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Update the existingQuestion with the new vote
        existingQuestion.addVote(vote); // Add vote to list
        if (vote.getAnswer()) {
            existingQuestion.setYesVotes(existingQuestion.getYesVotes() + 1);
        } else {
            existingQuestion.setNoVotes(existingQuestion.getNoVotes() + 1);
        }
        existingQuestion.setTotalVotes(existingQuestion.getTotalVotes() + 1);

        // Now save the updated question
        questionRepository.save(existingQuestion);


        // Now save the vote
        return voteRepository.save(vote);
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return voteRepository.existsById(id);
    }




}
