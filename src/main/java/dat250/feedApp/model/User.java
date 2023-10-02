package dat250.feedApp.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "AppUser")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Boolean adminRights;

    @OneToMany(mappedBy = "user")
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Poll> polls = new ArrayList<>();

    @Override
    public String toString() {
        return username;
    }

    // Add poll to user's list
    public void addPoll(Poll poll) {
        this.polls.add(poll);
        poll.setUser(this);
    }

    // Add vote to user's list
    public void addVote(Vote vote) {
        this.votes.add(vote);
        vote.setUser(this);
    }
}
