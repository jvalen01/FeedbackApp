package dat250.feedApp.controller;

import dat250.feedApp.model.Vote;
import dat250.feedApp.model.Poll;
import dat250.feedApp.service.VoteService;
import dat250.feedApp.service.WebSocketService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private VoteService voteService;
    private WebSocketService webSocketService;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public VoteController(VoteService voteService, WebSocketService webSocketService, RabbitTemplate rabbitTemplate) {
        this.voteService = voteService;
        this.webSocketService = webSocketService;
        this.rabbitTemplate = rabbitTemplate;
    }


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

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        Vote savedVote = voteService.saveVote(vote);
        rabbitTemplate.convertAndSend(exchange, routingkey, "Hello from RabbitMQ!");
        webSocketService.sendMessage("Hello World");  // This line sends the message to WebSocket subscribers
        return ResponseEntity.ok(voteService.saveVote(vote));
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
