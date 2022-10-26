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
    private final int GAME_FRAMES = 10;
    private String gameId;
    private String name;
    private int score;
    private List<FrameModel> gameFrames = new ArrayList<>(10);

    private boolean isGameOver = false;

    //TODO: instead of having this constructor we could perhaps use a builder annotation to handle it

    // public BowlingGameModel(String gameId, String name){
      // this.gameId = gameId;
      // this.name = name;
     // }

    //TODO: this needs to GO!
    public void init(){

        for(int i = 0; i < GAME_FRAMES; i++){

            gameFrames.add(new FrameModel());

        }

    }

    //TODO: 3 things : refactor for loop, conditionals are too messy and encapsulation (FrameModel) is being broken
    private void handleExtrasForSpareFrames(RollDto roll){

        for(int i = 0 ; i < gameFrames.size() ; i++){

            FrameModel currentFrame = gameFrames.get(i);

            if(currentFrame.isSpare() && currentFrame.getExtraRolls().isEmpty() && currentFrame.getInitialRolls().size() == MAX_NOT_LAST_FRAME_SIZE){

                currentFrame.getExtraRolls().add(roll);
                currentFrame.setFrameOpenForSpareRolls(false);

            }

        }

    }

    //TODO: 3 things : refactor for loop, conditionals are too messy and encapsulation (FrameModel) is being broken
    private void handleExtrasForStrikeFrames(RollDto roll){

        for(int i = 0 ; i < gameFrames.size() ; i++) {

            FrameModel currentFrame = gameFrames.get(i);

            if(currentFrame.isFrameOpenForStrikeRolls() && currentFrame.getExtraRolls().isEmpty()) {

                currentFrame.getExtraRolls().add(roll);

            } else if (currentFrame.isFrameOpenForStrikeRolls() && currentFrame.getExtraRolls().size() == 1) {

                currentFrame.getExtraRolls().add(roll);
                currentFrame.setFrameOpenForStrikeRolls(false);

            }

        }

    }

    //TODO: method naming -> is this name really describing what it does? Also Update loop
    private void tagAllFrames(){

        for(int i = 0 ; i < gameFrames.size() ; i++) {

            gameFrames.get(i).tagFrame();

        }

    }

    //TODO: Refactor loop
    private void calculateGameScore(){

        int sum = 0;

        for(int i = 0; i < gameFrames.size(); i++){

           int individualFrameScore = gameFrames.get(i).calculateFinalFrameValue();

            sum = sum + individualFrameScore;

        }

        score = sum;

    }

    //TODO: refactor loop
    private boolean checkIfFrameTotalWouldGoOverMaximum(FrameModel frame, RollDto roll){

        boolean isOverMax = false;

        if(!frame.getInitialRolls().isEmpty()){

            isOverMax = frame.calculateInitialFrameValue() + roll.pins() > MAX_NOT_LAST_FRAME_SCORE;

        }

        return isOverMax;

    }

    private void checkIfGameIsOver(){

        FrameModel lastGameFrame = gameFrames.get(gameFrames.size() - 1);

        //TODO: this conditional statement is WAY to big and unreadable: maybe we can handle this on a separate private method?
        if((lastGameFrame.isSpare() && !lastGameFrame.isFrameOpenForSpareRolls()) || (lastGameFrame.isStrike() && !lastGameFrame.isFrameOpenForStrikeRolls()) || (!lastGameFrame.isSpare() && !lastGameFrame.isStrike() && !lastGameFrame.isFrameOpenForInitialRolls() ) ){

            setGameOver(true);

        }

    }

    //TODO: this method needs an overhaul: looping this many times is not performant. Loops, statements and the likes need to be cleaner
   public void handleScoreCalculation(RollDto roll){

        for(int i = 0 ; i < gameFrames.size() ; i++){

            if(gameFrames.get(i).isFrameOpenForInitialRolls()){

                FrameModel currentFrame = gameFrames.get(i);

                if(checkIfFrameTotalWouldGoOverMaximum(currentFrame, roll)){

                    throw new IllegalSumOfRollsInFrameException("The sum of the rolls in this frame is illegal, please select a lower number of pins");

                }

                currentFrame.getInitialRolls().add(roll);

                if(currentFrame.getInitialRolls().size() == MAX_NOT_LAST_FRAME_SIZE || currentFrame.calculateInitialFrameValue() == MAX_NOT_LAST_FRAME_SCORE){

                    currentFrame.setFrameOpenForInitialRolls(false);

                }

                //TODO: This looks very ugly
                break;

            }

        }

        //TODO: this looks super ugly
        handleExtrasForSpareFrames(roll);
        handleExtrasForStrikeFrames(roll);

        calculateGameScore();
        tagAllFrames();

        checkIfGameIsOver();

   }

}
