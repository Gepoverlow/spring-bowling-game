package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.errorhandling.exception.BowlingGameNotFoundException;
import be.thebeehive.kata.api.model.BowlingGameModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BowlingGameRepository {

   public List<BowlingGameModel> bowlingGames = new ArrayList<>();

   public BowlingGameModel findBowlingGameByGameId(String gameId){

      BowlingGameModel foundBowlingGame = bowlingGames.stream().filter(game -> gameId.equals(game.getGameId())).findFirst().orElseThrow(()->new BowlingGameNotFoundException("Game with id " + gameId + " not found"));

      return foundBowlingGame;

   }

}
