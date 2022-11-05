package be.thebeehive.kata.api.entities;

import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.model.FrameModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BowlingGameEntity {

    @Id
    @GeneratedValue(generator = "system-uuid" )
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String gameId;
    private String name;
    private int score;
    private boolean isGameOver;
    @OneToMany(cascade = CascadeType.ALL)
    private List<FrameEntity> gameFrames = new ArrayList<>();
    private final int MAX_GAME_FRAMES = 10;

    public void handleScoreCalculation(RollEntity roll){

        RollEntity newRoll = new RollEntity(roll.getPins());

        if(gameHasNoFramesYet()){

            FrameEntity initialFrame = new FrameEntity();
            addFrameToGameFrames(initialFrame);

        }

        FrameEntity lastFrame = getLastGameFrame();

        if(gameIsOpenForAnotherFrame(lastFrame)) {

            FrameEntity newFrame = new FrameEntity();
            addFrameToGameFrames(newFrame);

        }

        handleAddingRollToOpenFrames(newRoll);
        calculateGameScore();
        checkIfGameIsOver();

    }

    private void calculateGameScore(){

        int sum = 0;

        for(FrameEntity frame : gameFrames){

            int individualFrameScore = frame.calculateFrameRollsValue();

            sum = sum + individualFrameScore;

        }

        score = sum;

    }

    private void checkIfGameIsOver(){

        if(gameFrames.size() == MAX_GAME_FRAMES){

            FrameEntity lastFrame = gameFrames.get(gameFrames.size() - 1);

            if(!lastFrame.isFrameOpenToAnyRoll()){

                isGameOver = true;

            }

        }

    }

    private boolean gameIsOpenForAnotherFrame(FrameEntity lastFrame){

        return (lastFrame.isSpare()
                || lastFrame.isStrike()
                || !lastFrame.isFrameOpenToAnyRoll())

                && (gameFrames.size() < MAX_GAME_FRAMES);

    }

    private boolean gameHasNoFramesYet(){

        return gameFrames.isEmpty();

    }

    private FrameEntity getLastGameFrame(){

        return gameFrames.get(gameFrames.size() - 1);

    }

    private void addFrameToGameFrames(FrameEntity newFrame){

        gameFrames.add(newFrame);

    }

    private void handleAddingRollToOpenFrames(RollEntity roll){

        gameFrames.forEach(frame -> {

            if(frame.isFrameOpenToAnyRoll()){

                frame.handleAddingNewRoll(roll);

            }

        });

    }

}
