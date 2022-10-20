package be.thebeehive.kata.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FrameModel {

    private final int MAX_PINS = 10;
    private final int MAX_INITIAL_SCORE = 10;
    private List<RollModel> initialRolls = new ArrayList<>();
    private List<RollModel> extraRolls = new ArrayList<>();

    private boolean isSpare = false;
    private boolean isStrike = false;
    private boolean isFrameOpenForInitialRolls = true;
    private boolean isFrameOpenForSpareRolls = false;
    private boolean isFrameOpenForStrikeRolls = false;

    public void tagFrame(){

        if(!this.getInitialRolls().isEmpty()){

            if(this.getInitialRolls().get(0).getPins() == MAX_PINS && this.calculateInitialFrameValue() == MAX_INITIAL_SCORE && this.extraRolls.size() < 2) {

                this.isStrike = true;
                this.setFrameOpenForStrikeRolls(true);

            } else if(this.getInitialRolls().get(0).getPins() != MAX_PINS && this.calculateInitialFrameValue() == MAX_INITIAL_SCORE && this.extraRolls.size() < 1){

                this.isSpare = true;
                this.setFrameOpenForSpareRolls(true);

            }

        }

    }

    public int calculateInitialFrameValue(){

        int sumOfInitialRolls = 0;

        for(int i = 0; i < this.initialRolls.size(); i++){

            sumOfInitialRolls = sumOfInitialRolls + this.initialRolls.get(i).getPins();

        }

        return sumOfInitialRolls;

    }

    private int calculateExtraRollsValue(){

        int sumOfExtraRolls = 0;

        for(int i = 0; i < this.extraRolls.size(); i++){

            sumOfExtraRolls = sumOfExtraRolls + this.extraRolls.get(i).getPins();

        }

        return sumOfExtraRolls;

    }

    public int calculateFinalFrameValue(){

        return this.calculateInitialFrameValue() + this.calculateExtraRollsValue();

    }

}
