package dat250.feedApp.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import dat250.feedApp.model.Poll;
import dat250.feedApp.model.User;
import dat250.feedApp.service.PollService;
import dat250.feedApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll, @RequestHeader(name = "Authorization") String idToken) {
        // You can use idToken here to associate the poll with the user
        // Extract the Bearer token
        idToken = idToken.replace("Bearer ", "");

        // Decode the ID token to get the Firebase UID
        String firebaseUID = getUidFromToken(idToken);
        // Find the user by Firebase UID
        User user = userService.findByFirebaseUID(firebaseUID)
                .orElseThrow(() -> new RuntimeException("User not found with Firebase UID: " + firebaseUID));

        // Associate the poll with the user
        poll.setUser(user);

        return pollService.save(poll);
    }

    public String getUidFromToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken.getUid();
        } catch (Exception e) {
            throw new RuntimeException("Error verifying ID token", e);
        }
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
}

