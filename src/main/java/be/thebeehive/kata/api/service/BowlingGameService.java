package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.model.BowlingGameModel;
import be.thebeehive.kata.api.model.RollModel;
import be.thebeehive.kata.api.repository.BowlingGameRepository;
import be.thebeehive.kata.api.util.BowlingGameStarterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BowlingGameService {

    private BowlingGameStarterMapper bowlingGameStarterMapper;
    private BowlingGameRepository bowlingGameRepository;

    public BowlingGameDto createBowlingGame(CreateGameDto createGameDto){

        BowlingGameModel bowlingGame = bowlingGameStarterMapper.toBowlingGameModel(createGameDto);
        BowlingGameDto bowlingGameDto = new BowlingGameDto(bowlingGame.getGameId(), bowlingGame.getName() , bowlingGame.getTotalGameScore());

        bowlingGame.init();

        bowlingGameRepository.bowlingGames.add(bowlingGame);

        return bowlingGameDto;

    }

    public BowlingGameDto performBowlingRoll(String gameId, RollDto rollDto){

        RollModel rollModel = bowlingGameStarterMapper.toRollModel(rollDto);
        BowlingGameModel foundBowlingGame = bowlingGameRepository.findBowlingGameByGameId(gameId);

        foundBowlingGame.handleScoreCalculation(rollModel);

        BowlingGameDto bowlingGameDto = new BowlingGameDto(foundBowlingGame.getGameId(), foundBowlingGame.getName(), foundBowlingGame.getTotalGameScore());

        return bowlingGameDto;

    }

}
