package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.model.BowlingGameModel;
import be.thebeehive.kata.api.util.BowlingGameStarterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BowlingGameService {

    private BowlingGameStarterMapper bowlingGameStarterMapper;

    public BowlingGameDto createBowlingGame(CreateGameDto createGameDto){

        BowlingGameModel bowlingGame = bowlingGameStarterMapper.toBowlingGameModel(createGameDto);
        BowlingGameDto bowlingGameDto = new BowlingGameDto(bowlingGame.getGameId(), bowlingGame.getName() , bowlingGame.getScore());

        return bowlingGameDto;

    }

}
