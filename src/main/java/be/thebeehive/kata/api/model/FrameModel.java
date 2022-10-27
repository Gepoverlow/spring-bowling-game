package be.thebeehive.kata.api.model;

import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.errorhandling.exception.IllegalSumOfRollsInFrameException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FrameModel {

    private final int STRIKE_FRAME_SIZE = 1;

    private final int SPARE_FRAME_SIZE = 2;
    private final int PIN_AMOUNT = 10;

    private List<RollDto> frameRolls = new ArrayList<>();

    private boolean isSpare = false;

    private boolean isStrike = false;

    private boolean isFrameOpenToAnyRoll = true;

    public int calculateFrameRollsValue(){

        int sumOfAllFrameRolls = 0;

        for(RollDto roll : frameRolls){

            sumOfAllFrameRolls = sumOfAllFrameRolls + roll.pins();

        }

        return sumOfAllFrameRolls;

    }

    public void handleAddingNewRoll(RollDto newRoll){

        if(isIllegalThrow(newRoll)){

            throw new IllegalSumOfRollsInFrameException("Illegal throw: there are only " + calculateRemainingPins() + "  pin/s remaining");

        }

        frameRolls.add(newRoll);
        updateFrameTags();

    }

    public void updateFrameTags(){

        int currentFrameScore = calculateFrameRollsValue();

        if(frameRolls.size() == STRIKE_FRAME_SIZE
            && currentFrameScore == PIN_AMOUNT){

            isStrike = true;

        } else if (frameRolls.size() == SPARE_FRAME_SIZE
                    && currentFrameScore == PIN_AMOUNT){

            isSpare = true;

        }

        checkIfFrameIsOpenForRolls(currentFrameScore);

    }

    public void checkIfFrameIsOpenForRolls(int currentScore){

        if(frameRolls.size() == 3
                && (isStrike || isSpare)){

            isFrameOpenToAnyRoll = false;

        } else if(frameRolls.size() == 2
                && currentScore != PIN_AMOUNT
                && !isStrike
                && !isSpare){

            isFrameOpenToAnyRoll = false;

        }

    }

    public boolean isIllegalThrow(RollDto roll){

        return !frameRolls.isEmpty()
                && !isStrike
                && !isSpare
                && frameRolls.get(0).pins() + roll.pins() > 10;

    }

    public int calculateRemainingPins(){

        return PIN_AMOUNT - frameRolls.get(0).pins();

    }

}
