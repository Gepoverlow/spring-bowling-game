package be.thebeehive.kata.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BowlingGameModel {

    private final int MIN_FRAME_SIZE = 1;
    private final int MAX_FRAME_SIZE = 2;

    private String gameId;
    private String name;
    private int totalGameScore = 0;
    private List<FrameModel> gameFrames = new ArrayList<>();

    public BowlingGameModel(String gameId, String name){
        this.gameId = gameId;
        this.name = name;
    }

    public void calculateGameScore(){

        int sum = 0;

        for(int i = 0; i < this.gameFrames.size(); i++){

           int individualFrameScore = this.gameFrames.get(i).calculateFinalFrameValue();

            sum = sum + individualFrameScore;

        }

        this.totalGameScore = sum;

    }

   public void handleScoreCalculation(RollModel roll){

        if(this.gameFrames.isEmpty()) {

            FrameModel firstFrame = new FrameModel();
            firstFrame.getInitialRolls().add(roll);

            this.gameFrames.add(firstFrame);

        } else {

            FrameModel lastFrame = this.gameFrames.get(this.gameFrames.size() - 1);

            if(lastFrame.getInitialRolls().size() == MIN_FRAME_SIZE) {

                RollModel secondRoll = new RollModel(roll.getPins());
                lastFrame.getInitialRolls().add(secondRoll);

            } else if(lastFrame.getInitialRolls().size() == MAX_FRAME_SIZE) {

                FrameModel newFrame = new FrameModel();
                RollModel firstRoll = new RollModel(roll.getPins());

                if(lastFrame.isFrameSpare()){

                    RollModel extraSpareRoll = new RollModel(roll.getPins());
                    lastFrame.getExtraRolls().add(extraSpareRoll);

                }

                newFrame.getInitialRolls().add(firstRoll);
                this.gameFrames.add(newFrame);

            }


        }

       this.calculateGameScore();

   }

}
