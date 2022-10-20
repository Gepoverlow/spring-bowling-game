package be.thebeehive.kata.api.model;

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

    public void handleExtrasForSpareFrames(RollModel roll){

        for(int i = 0 ; i < this.gameFrames.size() ; i++){

            FrameModel currentFrame = this.gameFrames.get(i);

            if(currentFrame.isSpare() && currentFrame.getExtraRolls().size() == 0 && currentFrame.getInitialRolls().size() == 2){

                currentFrame.getExtraRolls().add(roll);
                currentFrame.setFrameOpenForSpareRolls(false);

            }

        }

    }

    private void tagAllFrames(){

        for(int i = 0 ; i < this.gameFrames.size() ; i++) {

            this.gameFrames.get(i).tagFrame();

        }

    }

    public void handleExtrasForStrikeFrames(){}

    private void calculateGameScore(){

        int sum = 0;

        for(int i = 0; i < this.gameFrames.size(); i++){

           int individualFrameScore = this.gameFrames.get(i).calculateFinalFrameValue();

            sum = sum + individualFrameScore;

        }

        this.totalGameScore = sum;

    }

   public void handleScoreCalculation(RollModel roll){

        RollModel performedRoll = new RollModel(roll.getPins());

        for(int i = 0 ; i < this.gameFrames.size() ; i++){

            if(this.gameFrames.get(i).isFrameOpenForInitialRolls()){

                FrameModel currentFrame = this.gameFrames.get(i);

                currentFrame.getInitialRolls().add(performedRoll);

                if(currentFrame.getInitialRolls().size() == 2 || currentFrame.isSpare() || currentFrame.isStrike()){

                    currentFrame.setFrameOpenForInitialRolls(false);

                }

                break;

            }

        }

        this.handleExtrasForSpareFrames(roll);
       // this.handleExtrasForStrikeFrames(roll);

       this.calculateGameScore();
       this.tagAllFrames();

   }

}
