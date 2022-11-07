package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.dto.UpdateGameDto;
import be.thebeehive.kata.api.entities.BowlingGameEntity;
import be.thebeehive.kata.api.errorhandling.exception.BowlingGameNotFoundException;
import be.thebeehive.kata.api.errorhandling.exception.GameOverException;
import be.thebeehive.kata.api.mapper.BowlingGameMapper;
import be.thebeehive.kata.api.repository.BowlingGameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BowlingGameService {

    private final BowlingGameMapper bowlingGameMapper;
    private final BowlingGameRepository bowlingGameRepo;

    @Transactional
    public BowlingGameDto createBowlingGame(CreateGameDto createGameDto){

        BowlingGameEntity bowlingGameEntity = bowlingGameMapper.createGameDtoToBowlingGameEntity(createGameDto);

        bowlingGameRepo.save(bowlingGameEntity);

        return bowlingGameMapper.bowlingGameEntityToDto(bowlingGameEntity);

     }

     @Transactional
     public BowlingGameDto performBowlingRoll(String gameId, RollDto rollDto) {

         Optional<BowlingGameEntity> foundBowlingGameOptional = bowlingGameRepo.findById(gameId);

         if (foundBowlingGameOptional.isEmpty()) {

             throw new BowlingGameNotFoundException("Game with id " + gameId + " not found");

         }

         BowlingGameEntity foundBowlingGame = foundBowlingGameOptional.get();

         if (foundBowlingGame.isGameOver()) {

             throw new GameOverException("Game over. Final score is " + foundBowlingGame.getScore());

         }

         foundBowlingGame.handleScoreCalculation(bowlingGameMapper.rollDtoToRollEntity(rollDto));

        // bowlingGameRepo.save(foundBowlingGame);

         return bowlingGameMapper.bowlingGameEntityToDto(foundBowlingGame);

         }

         public BowlingGameDto updateGameName(String gameId, String newName){

            Optional<BowlingGameEntity> foundBowlingGameOpt = bowlingGameRepo.findById(gameId);

            if(foundBowlingGameOpt.isEmpty()){

                throw new BowlingGameNotFoundException("Game with id " + gameId + " not found");

            }

             BowlingGameEntity foundBowlingGame = foundBowlingGameOpt.get();

            foundBowlingGame.setName(newName);

           // bowlingGameRepo.save(foundBowlingGame);

            return bowlingGameMapper.bowlingGameEntityToDto(foundBowlingGame);

         }

}
