package dat250.feedApp.controller;

import dat250.feedApp.model.Vote;
import dat250.feedApp.service.VoteService;
import dat250.feedApp.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    // GET all votes
    @GetMapping
    public List<Vote> getAllVotes() {
        return voteService.findAllVotes();
    }

    // GET a single vote by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable Long id) {
        Optional<Vote> vote = voteService.findVoteById(id);
        if (vote.isPresent()) {
            return ResponseEntity.ok(vote.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // POST (create) a vote
    @Autowired
    private WebSocketService webSocketService;

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        try {
            Vote savedVote = voteService.saveVote(vote);
            Vote pollID = JSON.stringify(vote);
            if (savedVote != null) {
                // Get updated poll data after vote
                Poll updatedPoll = getUpdatedPoll(); // Implement this method to fetch updated poll data
                webSocketController.sendMessage(JSON.stringify(updatedPoll));
                return ResponseEntity.ok(savedVote);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT (update) a vote
    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long id, @RequestBody Vote updatedVote) {
        if (voteService.existsById(id)) {
            updatedVote.setId(id);  // Ensure the ID is set to the one from the path
            return ResponseEntity.ok(voteService.saveVote(updatedVote));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a vote
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        if (voteService.existsById(id)) {
            voteService.deleteVote(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
