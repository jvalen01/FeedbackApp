package dat250.feedApp.service;

import dat250.feedApp.model.Voter;
import dat250.feedApp.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Optional<Voter> getVoterById(Long id) {
        return voterRepository.findById(id);
    }

    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    public void deleteVoter(Long id) {
        voterRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return voterRepository.existsById(id);
    }
}
