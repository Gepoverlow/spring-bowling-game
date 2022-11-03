package be.thebeehive.kata.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollEntity {

    @Id
    private String rollId;

    @ManyToOne
    private FrameEntity parentFrame;

    private int pins;

    public RollEntity(int pins) {

    }
}
