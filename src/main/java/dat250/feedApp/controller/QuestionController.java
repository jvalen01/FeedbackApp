package dat250.feedApp.controller;

import dat250.feedApp.model.Question;
import dat250.feedApp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // GET all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // GET a single question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST (create) a question
    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    // PUT (update) a question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question updatedQuestion) {
        if (questionRepository.existsById(id)) {
            updatedQuestion.setId(id);  // Ensure the ID is set to the one from the path
            return ResponseEntity.ok(questionRepository.save(updatedQuestion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

