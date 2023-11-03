package dat250.feedApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
            // Deserialization
            logger.info("THIS IS THE MESSAGE: " + message);

            Vote vote = new Gson().fromJson(message, Vote.class);

            logger.info("Deserialized vote: " + vote);

            // Check for null values and print the details
            if (vote != null) {
                System.out.println(message);

                if (vote.getAnswer() != null) {
                    System.out.println(vote.getAnswer());
                } else {
                    System.out.println("Vote answer is null");
                }

                if (vote.getQuestion() != null) {
                    System.out.println(vote.getQuestion().getId());
                } else {
                    System.out.println("Vote question is null");
                }

                if (vote.getUser() != null) {
                    System.out.println(vote.getUser().getUsername());
                } else {
                    System.out.println("Vote user is null");
                }

                // Save the vote
                saveVote(vote);
            } else {
                System.out.println("Deserialized vote is null");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Null pointer exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }



    public Vote saveVote(Vote vote) {
        User existingUser = null;

        // Check if the vote's user is not null and its username is not null
        if (vote.getUser() != null && vote.getUser().getUsername() != null && !vote.getUser().getUsername().isEmpty()) {
            // This is an authenticated user
            String username = vote.getUser().getUsername();
            existingUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

            vote.setUser(existingUser);
            existingUser.addVote(vote);
            userRepository.save(existingUser);
        } else {
            // This is an anonymous voter
            Voter voter = new Voter();
            voter.setUsername("Anonymous-" + System.currentTimeMillis()); // Giving a unique identifier for the anonymous voter
            // First, save the voter entity
            voter = voterRepository.save(voter);
            voter.addVote(vote);
            vote.setVoter(voter);  // Set the vote's voter to this anonymous voter
        }

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
        System.out.println("SAVE!");
        return voteRepository.save(vote);
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return voteRepository.existsById(id);
    }




}
