package dat250.feedApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String firebaseUID; // The Firebase UID for the user

    private String username;

    private String password;

    private Boolean adminRights;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Vote> votes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
