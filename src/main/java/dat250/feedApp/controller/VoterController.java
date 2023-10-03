package dat250.feedApp.controller;

import dat250.feedApp.model.Voter;
import dat250.feedApp.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    @Autowired
    private VoterService voterService;

    // GET all voters
    @GetMapping
    public List<Voter> getAllVoters() {
        return voterService.getAllVoters();
    }

    // GET a single voter by ID
    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id) {
        return voterService.getVoterById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST (create) a voter
    @PostMapping
    public Voter createVoter(@RequestBody Voter voter) {
        return voterService.saveVoter(voter);
    }

    // PUT (update) a voter
    @PutMapping("/{id}")
    public ResponseEntity<Voter> updateVoter(@PathVariable Long id, @RequestBody Voter updatedVoter) {
        if (voterService.existsById(id)) {
            updatedVoter.setId(id); // Ensure the ID is set to the one from the path
            return ResponseEntity.ok(voterService.saveVoter(updatedVoter));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a voter
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoter(@PathVariable Long id) {
        if (voterService.existsById(id)) {
            voterService.deleteVoter(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

