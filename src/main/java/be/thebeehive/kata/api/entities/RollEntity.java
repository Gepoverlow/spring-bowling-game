package be.thebeehive.kata.api.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RollEntity {

    @Id
    @GeneratedValue(generator = "system-uuid" )
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String rollId;
    private int pins;

    public RollEntity(int pins){

        this.pins = pins;

    }

}
