package be.thebeehive.kata.api.model;

import be.thebeehive.kata.api.dto.RollDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FrameModel {

    private final int MAX_PINS = 10;
    private final int MAX_INITIAL_SCORE = 10;
    //TODO: maybe these two lists could become a single one?
    private List<RollDto> initialRolls = new ArrayList<>();
    private List<RollDto> extraRolls = new ArrayList<>();

    private boolean isSpare = false;
    private boolean isStrike = false;

    //TODO this boolean could just become isFrameOpenToAnyRoll to get initial and extra rolls on a single place and check
    private boolean isFrameOpenForInitialRolls = true;

    //TODO: are these two booleans really needed if we already tag the frame with isSpare or isStrike?
    private boolean isFrameOpenForSpareRolls = false;
    private boolean isFrameOpenForStrikeRolls = false;

    //TODO: this method needs a rework for: nested, long and ugly conditionals
    public void tagFrame(){

        if(!this.getInitialRolls().isEmpty()){

            if(this.getInitialRolls().get(0).pins() == MAX_PINS && this.calculateInitialFrameValue() == MAX_INITIAL_SCORE && this.extraRolls.size() < 2) {

                this.isStrike = true;
                this.setFrameOpenForStrikeRolls(true);

            } else if(this.getInitialRolls().get(0).pins() != MAX_PINS && this.calculateInitialFrameValue() == MAX_INITIAL_SCORE && this.extraRolls.size() < 1){

                this.isSpare = true;
                this.setFrameOpenForSpareRolls(true);

            }

        }

    }

    //TODO: rework for loop for a more modern approach
    public int calculateInitialFrameValue(){

        int sumOfInitialRolls = 0;

        for(int i = 0; i < this.initialRolls.size(); i++){

            sumOfInitialRolls = sumOfInitialRolls + this.initialRolls.get(i).pins();

        }

        return sumOfInitialRolls;

    }

    //TODO: If I can manage to merge the initial and extra rolls on a single list this will not be needed
    private int calculateExtraRollsValue(){

        int sumOfExtraRolls = 0;

        for(int i = 0; i < this.extraRolls.size(); i++){

            sumOfExtraRolls = sumOfExtraRolls + this.extraRolls.get(i).pins();

        }

        return sumOfExtraRolls;

    }

    //TODO: If I can manage to merge the initial and extra rolls on a single list this will not be needed
    public int calculateFinalFrameValue(){

        return this.calculateInitialFrameValue() + this.calculateExtraRollsValue();

    }

}
