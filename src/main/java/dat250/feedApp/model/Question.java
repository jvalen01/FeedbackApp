package dat250.feedApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private int yesVotes;

    private int noVotes;

    private int totalVotes;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Poll poll;

    @OneToMany(mappedBy = "question")
    private List<Vote> votes = new ArrayList<>();

    @Override
    public String toString() {
        return this.question;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
        vote.setQuestion(this);
    }


}
