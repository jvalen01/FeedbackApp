package dat250.feedApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Poll {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean active;

    private String accessMode;

    @OneToMany(mappedBy = "poll")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "poll")
    private List<IoTDevice> ioTDevices = new ArrayList<>();

    @ManyToOne
    private User user;

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setPoll(this);
    }

}
