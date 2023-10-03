package dat250.feedApp.service;

import dat250.feedApp.model.Question;
import dat250.feedApp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public boolean existsById(Long id) {
        return questionRepository.existsById(id);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
