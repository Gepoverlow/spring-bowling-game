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

    public int calculateFinalFrameValue(){

        int sumOfExtraRolls = 0;
        int sumOfInitialRolls = 0;

        for(int i = 0; i < this.extraRolls.size(); i++){

            sumOfExtraRolls = sumOfInitialRolls + this.extraRolls.get(i).getPins();

        }

        for(int i = 0; i < this.initialRolls.size(); i++){

            sumOfInitialRolls = sumOfInitialRolls + this.initialRolls.get(i).getPins();

        }


        return sumOfExtraRolls + sumOfInitialRolls;

    }

}
