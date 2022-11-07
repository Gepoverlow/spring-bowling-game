package be.thebeehive.kata.api.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BowlingGameEntityTest {

    private BowlingGameEntity bowlingGameToBeTested;

    @BeforeEach
    public void setUp(){

        bowlingGameToBeTested = new BowlingGameEntity();

    }

    @Test
    public void shouldHandleScoreCalculationOfSingleRoll(){

        //given
        RollEntity newRoll = new RollEntity(9);

        //when
        bowlingGameToBeTested.handleScoreCalculation(newRoll);
        int expected = 9;

        //then
        assertThat(bowlingGameToBeTested.getScore()).isEqualTo(expected);

    }

}
