package dat250.feedApp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private int yesVotes;

    private int noVotes;

    private int totalVotes;

    @ManyToOne
    private Poll poll;

    @OneToMany(mappedBy = "question")
    private List<Vote> votes = new ArrayList<>();

    @Override
    public String toString() {
        return this.question;
    }


}
