package be.thebeehive.kata.api.util;

import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.model.BowlingGameModel;
import be.thebeehive.kata.api.model.RollModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BowlingGameStarterMapper {

    GameIdGenerator gameIdGenerator;

    public BowlingGameModel toBowlingGameModel(CreateGameDto createGameDto){

        String gameId = gameIdGenerator.generateGameId();

        return new BowlingGameModel(gameId, createGameDto.name());

    }

    public RollModel toRollModel(RollDto rollDto){

        return new RollModel(rollDto.pins());

    }

}
