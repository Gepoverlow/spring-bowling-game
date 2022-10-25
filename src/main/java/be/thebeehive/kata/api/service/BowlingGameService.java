package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.errorhandling.exception.GameOverException;
import be.thebeehive.kata.api.model.BowlingGameModel;
import be.thebeehive.kata.api.model.RollModel;
import be.thebeehive.kata.api.repository.BowlingGameRepository;
import be.thebeehive.kata.api.util.BowlingGameStarterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BowlingGameService {

    private final BowlingGameStarterMapper bowlingGameStarterMapper;
    private final BowlingGameRepository bowlingGameRepository;

    public BowlingGameDto createBowlingGame(CreateGameDto createGameDto){

        BowlingGameModel bowlingGame = bowlingGameStarterMapper.toBowlingGameModel(createGameDto);

        //TODO: this needs to go
        bowlingGame.init();

        //TODO: this is not good practice : maybe create a public method that handles this?
        bowlingGameRepository.bowlingGames.add(bowlingGame);

        return new BowlingGameDto(bowlingGame.getGameId(), bowlingGame.getName() , bowlingGame.getTotalGameScore());

    }

    public BowlingGameDto performBowlingRoll(String gameId, RollDto rollDto){

        BowlingGameModel foundBowlingGame = bowlingGameRepository.findBowlingGameByGameId(gameId);

        if(foundBowlingGame.isGameOver()){
            throw new GameOverException("Game over. Final score is " + foundBowlingGame.getTotalGameScore());
        }

        foundBowlingGame.handleScoreCalculation(rollDto);

       return new BowlingGameDto(foundBowlingGame.getGameId(), foundBowlingGame.getName(), foundBowlingGame.getTotalGameScore());

    }

}
