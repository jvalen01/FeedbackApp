package dat250.feedApp.service;

import dat250.feedApp.model.Poll;
import dat250.feedApp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public Optional<Poll> findById(Long id) {
        return pollRepository.findById(id);
    }

    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    public boolean existsById(Long id) {
        return pollRepository.existsById(id);
    }

    public void deleteById(Long id) {
        pollRepository.deleteById(id);
    }

    public Optional<Poll> findByCode(String code) {
        return pollRepository.findByCode(code);
    }
}

