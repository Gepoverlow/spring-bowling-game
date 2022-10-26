package be.thebeehive.kata.api.model;

import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.errorhandling.exception.IllegalSumOfRollsInFrameException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//TODO: PUBLIC methods need to go on top
public class BowlingGameModel {

    //TODO: review these variable naming and utility
    private final int MAX_NOT_LAST_FRAME_SIZE = 2;
    private final int MAX_NOT_LAST_FRAME_SCORE = 10;
    private final int MAX_LAST_FRAME_SCORE = 30;
    private final int MAX_GAME_FRAMES = 10;
    private String gameId;
    private String name;
    private int score;
    private List<FrameModel> gameFrames = new ArrayList<>();

    private boolean isGameOver = false;

    //TODO: instead of having this constructor we could perhaps use a builder annotation to handle it

    // public BowlingGameModel(String gameId, String name){
      // this.gameId = gameId;
      // this.name = name;
     // }

    public void handleScoreCalculation(RollDto roll){

        RollDto newRoll = new RollDto(roll.pins());

        if(gameFrames.isEmpty()){

            FrameModel initialFrame = new FrameModel();
            gameFrames.add(initialFrame);

        }

        FrameModel lastFrame = gameFrames.get(gameFrames.size() - 1);

        if(lastFrame.isSpare()
                || lastFrame.isStrike()
                || !lastFrame.isFrameOpenToAnyRoll() ) {

            if(gameFrames.size() <= 9) {

                FrameModel newFrame = new FrameModel();
                gameFrames.add(newFrame);

            }

        }

        gameFrames.forEach(frame -> {

            if(frame.isFrameOpenToAnyRoll()){

                frame.handleAddingNewRoll(newRoll);

            }

        });

        calculateGameScore();
        checkIfGameIsOver();

    }

    private void calculateGameScore(){

        int sum = 0;

        for(FrameModel frame : gameFrames){

           int individualFrameScore = frame.calculateFrameRollsValue();

            sum = sum + individualFrameScore;

        }

        score = sum;

    }

    private void checkIfGameIsOver(){

        if(gameFrames.size() == MAX_GAME_FRAMES){

            FrameModel lastFrame = gameFrames.get(gameFrames.size() - 1);

            if(!lastFrame.isFrameOpenToAnyRoll()){

                isGameOver = true;

            }

        }

    }

}
