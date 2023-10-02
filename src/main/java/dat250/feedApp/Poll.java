package dat250.feedApp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

}
