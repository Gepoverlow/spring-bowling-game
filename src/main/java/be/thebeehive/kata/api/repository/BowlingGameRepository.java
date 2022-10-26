package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.errorhandling.exception.BowlingGameNotFoundException;
import be.thebeehive.kata.api.model.BowlingGameModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BowlingGameRepository {

   public Map<String, BowlingGameModel> bowlingGames = new HashMap();

   public BowlingGameModel findBowlingGameByGameId(String gameId){

      if(bowlingGames.get(gameId) == null) {

         throw new BowlingGameNotFoundException("Game with id " + gameId + " not found");

      }

      return bowlingGames.get(gameId);

   }

   public void addNewBowlingGame(String gameId, BowlingGameModel newBowlingGame){

      bowlingGames.put(gameId, newBowlingGame);

   }

}
