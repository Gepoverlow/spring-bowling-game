package be.thebeehive.kata.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BowlingGameModel {
    private String gameId;
    private String name;
    private int score;
}
