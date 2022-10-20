package be.thebeehive.kata.api.model;

import be.thebeehive.kata.api.errorhandling.exception.IllegalSumOfRollsInFrameException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BowlingGameModel {

    private final int MIN_FRAME_SIZE = 1;
    private final int MAX_FRAME_SIZE = 3;
    private final int GAME_FRAMES = 10;

    private String gameId;
    private String name;
    private int totalGameScore = 0;
    private List<FrameModel> gameFrames = new ArrayList<>(10);

    public BowlingGameModel(String gameId, String name){
        this.gameId = gameId;
        this.name = name;
    }

    public void init(){

        for(int i = 0; i < GAME_FRAMES; i++){

            this.gameFrames.add(new FrameModel());

        }

    }

    private void handleExtrasForSpareFrames(RollModel roll){

        for(int i = 0 ; i < this.gameFrames.size() ; i++){

            FrameModel currentFrame = this.gameFrames.get(i);

            if(currentFrame.isSpare() && currentFrame.getExtraRolls().size() == 0 && currentFrame.getInitialRolls().size() == 2){

                currentFrame.getExtraRolls().add(roll);
                currentFrame.setFrameOpenForSpareRolls(false);

            }

        }

    }

    private void handleExtrasForStrikeFrames(RollModel roll){

        for(int i = 0 ; i < this.gameFrames.size() ; i++) {

            FrameModel currentFrame = this.gameFrames.get(i);

            if(currentFrame.isFrameOpenForStrikeRolls() && currentFrame.getExtraRolls().size() == 0) {

                currentFrame.getExtraRolls().add(roll);

            } else if (currentFrame.isFrameOpenForStrikeRolls() && currentFrame.getExtraRolls().size() == 1) {

                currentFrame.getExtraRolls().add(roll);
                currentFrame.setFrameOpenForStrikeRolls(false);

            }

        }

    }

    private void tagAllFrames(){

        for(int i = 0 ; i < this.gameFrames.size() ; i++) {

            this.gameFrames.get(i).tagFrame();

        }

    }

    private void calculateGameScore(){

        int sum = 0;

        for(int i = 0; i < this.gameFrames.size(); i++){

           int individualFrameScore = this.gameFrames.get(i).calculateFinalFrameValue();

            sum = sum + individualFrameScore;

        }

        this.totalGameScore = sum;

    }

    private boolean checkIfFrameTotalWouldGoOverTen(FrameModel frame, RollModel roll){

        boolean isOverTen = false;

        if(!frame.getInitialRolls().isEmpty()){

            isOverTen = frame.getInitialRolls().get(0).getPins() + roll.getPins() > 10;

        }

        return isOverTen;

    }

   public void handleScoreCalculation(RollModel roll){

        for(int i = 0 ; i < this.gameFrames.size() ; i++){

            if(this.gameFrames.get(i).isFrameOpenForInitialRolls()){

                FrameModel currentFrame = this.gameFrames.get(i);

                if(this.checkIfFrameTotalWouldGoOverTen(currentFrame, roll)){

                    throw new IllegalSumOfRollsInFrameException("The sum of the rolls in this frame is illegal, please select a lower number of pins");

                }

                currentFrame.getInitialRolls().add(roll);

                if(currentFrame.getInitialRolls().size() == 2 || currentFrame.calculateInitialFrameValue() == 10){

                    currentFrame.setFrameOpenForInitialRolls(false);

                }

                break;

            }

        }

        this.handleExtrasForSpareFrames(roll);
        this.handleExtrasForStrikeFrames(roll);

        this.calculateGameScore();
        this.tagAllFrames();

   }

}
