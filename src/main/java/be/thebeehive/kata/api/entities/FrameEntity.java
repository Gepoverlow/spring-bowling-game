package be.thebeehive.kata.api.entities;

import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.errorhandling.exception.IllegalSumOfRollsInFrameException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class FrameEntity {

    @Id
    private String frameId;

    @ManyToOne
    private BowlingGameEntity parentGame;

    private final int STRIKE_FRAME_SIZE = 1;
    private final int SPARE_FRAME_SIZE = 2;
    private final int TOTAL_SPECIAL_FRAME_ROLLS = 3;
    private final int TOTAL_REGULAR_FRAME_ROLLS = 2;
    private final int PIN_AMOUNT = 10;
    @OneToMany
    private List<RollEntity> frameRolls = new ArrayList<>();
    private boolean isSpare = false;
    private boolean isStrike = false;
    private boolean isFrameOpenToAnyRoll = true;

    public int calculateFrameRollsValue(){

        int sumOfAllFrameRolls = 0;

        for(RollEntity roll : frameRolls){

            sumOfAllFrameRolls = sumOfAllFrameRolls + roll.getPins();

        }

        return sumOfAllFrameRolls;

    }

    public void handleAddingNewRoll(RollEntity newRoll){

        if(isIllegalThrow(newRoll)){

            throw new IllegalSumOfRollsInFrameException("Illegal throw: there are only " + calculateRemainingPins() + "  pin/s remaining");

        }

        frameRolls.add(newRoll);
        updateFrameTags();

    }

    private void updateFrameTags(){

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

    private void checkIfFrameIsOpenForRolls(int currentScore){

        if(frameRolls.size() == TOTAL_SPECIAL_FRAME_ROLLS
                && (isStrike || isSpare)){

            isFrameOpenToAnyRoll = false;

        } else if(frameRolls.size() == TOTAL_REGULAR_FRAME_ROLLS
                && currentScore != PIN_AMOUNT
                && !isStrike
                && !isSpare){

            isFrameOpenToAnyRoll = false;

        }

    }

    private boolean isIllegalThrow(RollEntity roll){

        return !frameRolls.isEmpty()
                && !isStrike
                && !isSpare
                && frameRolls.get(0).getPins() + roll.getPins() > PIN_AMOUNT;

    }

    private int calculateRemainingPins(){

        return PIN_AMOUNT - frameRolls.get(0).getPins();

    }

}

