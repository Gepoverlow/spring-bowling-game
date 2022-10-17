package be.thebeehive.kata.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BowlingGameModel {
    private String gameId;
    private String name;
    private int score = 0;
    private List<RollModel> gameRolls = new ArrayList<>();

    public BowlingGameModel(String gameId, String name){
        this.gameId = gameId;
        this.name = name;
    }

    public List<RollModel> addRoll(RollModel roll){
         this.gameRolls.add(roll);
         return this.gameRolls;
    }

    public void calculateGameScore(List<RollModel> allRolls){

        int sum = 0;

        for(int i = 0; i < allRolls.size(); i++){

            sum = sum + allRolls.get(i).getPins();

        }

        this.score = sum;

    }

}
