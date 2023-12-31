package dat250.feedApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class IoTDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer identifier;

    private Boolean status;

    @ManyToOne
    private Poll poll;

    @OneToMany(mappedBy = "ioTDevice")
    private List<Vote> votes = new ArrayList<>();


}
