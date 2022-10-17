package be.thebeehive.kata.api.util;

import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.model.BowlingGameModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BowlingGameStarterMapper {

    GameIdGenerator gameIdGenerator;
    private final int STARTING_SCORE = 0;

    public BowlingGameModel toBowlingGameModel(CreateGameDto createGameDto){

        String gameId = gameIdGenerator.generateGameId();

        return new BowlingGameModel(gameId, createGameDto.name(), STARTING_SCORE);

    }

}
