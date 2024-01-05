package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "city")
public class City extends BaseEntity {

    @Column(name = "name")
    protected String name;

    @OneToMany(mappedBy = "city")
    List<Weather> weathers;
}
