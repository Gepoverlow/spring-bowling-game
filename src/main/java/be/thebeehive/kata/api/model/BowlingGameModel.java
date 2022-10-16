package be.thebeehive.kata.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class BowlingGameModel {
    private String gameId;
    private String name;
    private int score;
}
