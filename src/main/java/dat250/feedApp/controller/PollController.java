package dat250.feedApp.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import dat250.feedApp.model.Poll;
import dat250.feedApp.model.Question;
import dat250.feedApp.model.User;
import dat250.feedApp.service.PollService;
import dat250.feedApp.service.QuestionService;
import dat250.feedApp.service.UserService;
import dat250.feedApp.utils.FirebaseFunctions;
import dat250.feedApp.utils.PollCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;


    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long id) {
        Optional<Poll> poll = pollService.findById(id);
        if (poll.isPresent()) {
            return ResponseEntity.ok(poll.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/code/{code}")
    public ResponseEntity<Poll> getPollByCode(@PathVariable String code) {
        Optional<Poll> poll = pollService.findByCode(code);
        if (poll.isPresent()) {
            return ResponseEntity.ok(poll.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Poll>> getPollsByUser(@RequestHeader(name = "Authorization") String idToken) {
        idToken = idToken.replace("Bearer ", "");
        String firebaseUID = FirebaseFunctions.getUidFromToken(idToken);
        User user = userService.findByFirebaseUID(firebaseUID)
                .orElseThrow(() -> new RuntimeException("User not found with Firebase UID: " + firebaseUID));
        List<Poll> polls = pollService.findByUser(user);
        System.out.println(polls);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(polls, headers, HttpStatus.OK);
    }


    @PostMapping
    public Poll createPoll(@RequestBody Poll poll, @RequestHeader(name = "Authorization") String idToken) {
        // Extract the Bearer token
        idToken = idToken.replace("Bearer ", "");

        // Decode the ID token to get the Firebase UID
        String firebaseUID = FirebaseFunctions.getUidFromToken(idToken);

        // Find the user by Firebase UID
        User user = userService.findByFirebaseUID(firebaseUID)
                .orElseThrow(() -> new RuntimeException("User not found with Firebase UID: " + firebaseUID));

        // Associate the poll with the user
        poll.setUser(user);

        // Generate a unique code for the poll
        String uniqueCode = PollCodeGenerator.generateCode();
        poll.setCode(uniqueCode);

        return pollService.save(poll);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Poll> updatePoll(@PathVariable Long id, @RequestBody Poll updatedPoll) {
        if (pollService.existsById(id)) {
            updatedPoll.setId(id);
            return ResponseEntity.ok(pollService.save(updatedPoll));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        if (pollService.existsById(id)) {
            pollService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Poll> deactivatePoll(@PathVariable Long id) {
        if (pollService.existsById(id)) {
            return ResponseEntity.ok(pollService.setPollActiveState(id, false));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

