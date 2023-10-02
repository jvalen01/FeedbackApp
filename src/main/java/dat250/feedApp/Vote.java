package dat250.feedApp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean answer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Question question;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private IoTDevice ioTDevice;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Voter voter;


}
