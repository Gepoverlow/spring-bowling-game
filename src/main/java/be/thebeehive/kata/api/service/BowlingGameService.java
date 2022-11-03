package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.errorhandling.exception.GameOverException;
import be.thebeehive.kata.api.mapper.BowlingGameMapper;
import be.thebeehive.kata.api.model.BowlingGameModel;
import be.thebeehive.kata.api.repository.BowlingGameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BowlingGameService {

   private final BowlingGameMapper bowlingGameMapper;
   private final BowlingGameRepository bowlingGameRepository;

  //  public BowlingGameDto createBowlingGame(CreateGameDto createGameDto){

       // return bowlingGameRepository.addNewBowlingGame(bowlingGameMapper.createGameDtoToBowlingGameModel(createGameDto));

   // }

    public BowlingGameDto performBowlingRoll(String gameId, RollDto rollDto){

        BowlingGameModel foundBowlingGame = bowlingGameRepository.findBowlingGameByGameId(gameId);

        if(foundBowlingGame.isGameOver()){

            throw new GameOverException("Game over. Final score is " + foundBowlingGame.getScore());

        }

        foundBowlingGame.handleScoreCalculation(rollDto);

       return new BowlingGameDto(foundBowlingGame.getGameId(), foundBowlingGame.getName(), foundBowlingGame.getScore());

    }

    public BowlingGameDto updateGameName(String gameId, String newName){

        BowlingGameModel foundBowlingGame = bowlingGameRepository.findBowlingGameByGameId(gameId);

        foundBowlingGame.setName(newName);

        return new BowlingGameDto(foundBowlingGame.getGameId(), foundBowlingGame.getName(), foundBowlingGame.getScore());

    }


}
