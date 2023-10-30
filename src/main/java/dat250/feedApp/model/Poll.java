package dat250.feedApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Poll {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean active;

    private String accessMode;

    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id") // This specifies the column for the foreign key
    private Question question;

    @OneToMany(mappedBy = "poll")
    private List<IoTDevice> ioTDevices = new ArrayList<>();

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", accessMode='" + accessMode + '\'' +
                ", question='" + question + '\'' +
                ", code='" + code + '\'' +
                ", ioTDevices=" + ioTDevices +
                ", user=" + user +
                '}';
    }

}
