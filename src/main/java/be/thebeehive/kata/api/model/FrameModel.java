package be.thebeehive.kata.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FrameModel {

    private final int MAX_PINS = 10;

    private List<RollModel> initialRolls = new ArrayList<>();
    private List<RollModel> extraRolls = new ArrayList<>();
    private int frameScore;

    public boolean isFrameSpare(){

        return this.checkForSpare();

    }

    private boolean checkForSpare(){

            return this.initialRolls.get(0).getPins() != 10 && this.calculateInitialFrameValue() == 10;

    }

    private int calculateInitialFrameValue(){

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
