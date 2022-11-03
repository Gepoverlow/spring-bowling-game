package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.entities.BowlingGameEntity;
import be.thebeehive.kata.api.errorhandling.exception.BowlingGameNotFoundException;
import be.thebeehive.kata.api.errorhandling.exception.GameOverException;
import be.thebeehive.kata.api.mapper.BowlingGameMapper;
import be.thebeehive.kata.api.repository.BowlingGameRepositoryV2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BowlingGameServiceV2 {

    private final BowlingGameMapper bowlingGameMapper;
    private final BowlingGameRepositoryV2 bowlingGameRepo;

    public BowlingGameDto createBowlingGame(CreateGameDto createGameDto){

        BowlingGameEntity bowlingGameEntity = bowlingGameMapper.createGameDtoToBowlingGameEntity(createGameDto);

        bowlingGameRepo.save(bowlingGameEntity);

        return bowlingGameMapper.bowlingGameEntityToDto(bowlingGameEntity);

     }

     public BowlingGameDto performBowlingRoll(String gameId, RollDto rollDto){

        BowlingGameEntity foundBowlingGame = bowlingGameRepo.findByGameId(gameId);

        if(foundBowlingGame == null){

            throw new BowlingGameNotFoundException("Game with id " + gameId + " not found");

        }

        if(foundBowlingGame.isGameOver()){

            throw new GameOverException("Game over. Final score is " + foundBowlingGame.getScore());

        }

        foundBowlingGame.handleScoreCalculation(bowlingGameMapper.rollDtoToRollEntity(rollDto));

        return bowlingGameMapper.bowlingGameEntityToDto(foundBowlingGame);

     }

}
