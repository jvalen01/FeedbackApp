package dat250.feedApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean answer;

    @ManyToOne
    private Question question;

    @ManyToOne
    private IoTDevice ioTDevice;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    private Voter voter;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", answer=" + answer +
                ", question=" + question +
                ", ioTDevice=" + ioTDevice +
                ", user=" + user +
                ", voter=" + voter +
                '}';
    }
}
