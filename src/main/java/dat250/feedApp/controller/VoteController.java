package dat250.feedApp.controller;

import com.google.api.client.json.Json;
import dat250.feedApp.model.Payload;
import dat250.feedApp.model.Vote;
import dat250.feedApp.model.Poll;
import dat250.feedApp.service.VoteService;
import dat250.feedApp.service.WebSocketService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
public class RabbitVote{

    public String username;
    public Vote vote;

    public RabbitVote(String username, Vote vote) {
        this.username = username;
        this.vote = vote;
    }
}

    @PostMapping
    public ResponseEntity<String> createVote(@RequestBody Vote vote) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        RabbitVote Rvote = new RabbitVote(vote.getUser().toString(), vote);
        rabbitTemplate.convertAndSend(exchange, routingkey, Rvote);
        webSocketService.sendMessage("Vote received and will be processed!");  // Optional: inform WebSocket subscribers
        return ResponseEntity.ok("Vote received and is being processed");
    }


    // PUT (update) a vote
    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long id, @RequestBody Vote updatedVote) {
        if (voteService.existsById(id)) {
            updatedVote.setId(id);
            Payload payload = new Payload();
            payload.username = updatedVote.getUser().toString();
            payload.vote = updatedVote;// Ensure the ID is set to the one from the path
            return ResponseEntity.ok(voteService.saveVote(payload));
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
