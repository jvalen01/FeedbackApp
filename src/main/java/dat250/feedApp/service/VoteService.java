package dat250.feedApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dat250.feedApp.model.Payload;
import com.google.gson.JsonSyntaxException;

import dat250.feedApp.model.Question;
import dat250.feedApp.model.Vote;
import dat250.feedApp.model.Voter;
import dat250.feedApp.model.User;
import dat250.feedApp.repository.QuestionRepository;
import dat250.feedApp.repository.UserRepository;
import dat250.feedApp.repository.VoteRepository;
import dat250.feedApp.repository.VoterRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    @Autowired
    private VoterRepository voterRepository;
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);


    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }

    public Optional<Vote> findVoteById(Long id) {
        return voteRepository.findById(id);
    }
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(String message) {
        try {
            Payload payload = new Gson().fromJson(message, Payload.class);
            saveVote(payload);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public Vote saveVote(Payload payload) {
        Vote vote = payload.getVote();
        String username = payload.getUsername();
        System.out.println(username);
        System.out.println(vote.toString());
        System.out.println("LETS GO!");
        User existingUser = null;
        existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        vote.setUser(existingUser);
        existingUser.addVote(vote);
        userRepository.save(existingUser);

    // Fetch the current state of the Question entity from the database
    Question existingQuestion = questionRepository.findById(vote.getQuestion().getId())
            .orElseThrow(() -> new RuntimeException("Question not found"));

    // Update the existingQuestion with the new vote
        logger.info("Adding vote to existing question...");
        existingQuestion.addVote(vote); // Add vote to list

        logger.info("Updating question's vote counts...");
        if (vote.getAnswer()) {
        existingQuestion.setYesVotes(existingQuestion.getYesVotes() + 1);
    } else {
        existingQuestion.setNoVotes(existingQuestion.getNoVotes() + 1);
    }
        existingQuestion.setTotalVotes(existingQuestion.getTotalVotes() + 1);

        logger.info("Saving updated question...");
    // Now save the updated question
        questionRepository.save(existingQuestion);

        logger.info("Saving vote...");
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
