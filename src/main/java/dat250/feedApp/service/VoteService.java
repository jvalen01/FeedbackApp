package dat250.feedApp.service;

import dat250.feedApp.model.Vote;
import dat250.feedApp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }

    public Optional<Vote> findVoteById(Long id) {
        return voteRepository.findById(id);
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return voteRepository.existsById(id);
    }
}
