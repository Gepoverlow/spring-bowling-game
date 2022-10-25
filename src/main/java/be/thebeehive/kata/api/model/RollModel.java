package be.thebeehive.kata.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
//TODO: this class is redundant and can be replaced by the RollDto
public class RollModel {
    private int pins;
}
