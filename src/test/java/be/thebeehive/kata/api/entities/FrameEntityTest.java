package be.thebeehive.kata.api.entities;

import be.thebeehive.kata.api.errorhandling.exception.IllegalSumOfRollsInFrameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FrameEntityTest {

    private FrameEntity frameToBeTested;


    @BeforeEach
    public void setUp(){

        frameToBeTested = new FrameEntity();

    }

    @Test
    public void shouldReturnTheSumOfAllRollsWithin(){

        //given
        RollEntity firstRoll = new RollEntity(5);
        RollEntity secondRoll = new RollEntity(2);
        frameToBeTested.getFrameRolls().add(firstRoll);
        frameToBeTested.getFrameRolls().add(secondRoll);

        //when
        int sumOfAllRollValuesWithinFrame = frameToBeTested.calculateFrameRollsValue();
        int expected = 7;

        //then
        assertThat(sumOfAllRollValuesWithinFrame).isEqualTo(expected);

    }

    @Test
    public void shouldHandleAddingANewLegalNonStrikeNonSpareRoll(){

        //given
        RollEntity firstRoll = new RollEntity(4);
        RollEntity secondRoll = new RollEntity(1);
        frameToBeTested.getFrameRolls().add(firstRoll);

        //when
        frameToBeTested.handleAddingNewRoll(secondRoll);
        int expected = 5;

        //then
        assertThat(frameToBeTested.calculateFrameRollsValue()).isEqualTo(expected);
        assertThat(frameToBeTested.isFrameOpenToAnyRoll()).isFalse();

    }

    @Test
    public void shouldHandleAddingANewLegalSpareRoll(){

        //given
        RollEntity firstRoll = new RollEntity(5);
        RollEntity secondRoll = new RollEntity(5);
        frameToBeTested.getFrameRolls().add(firstRoll);

        //when
        frameToBeTested.handleAddingNewRoll(secondRoll);
        int expected = 10;

        //then
        assertThat(frameToBeTested.calculateFrameRollsValue()).isEqualTo(expected);
        assertThat(frameToBeTested.isFrameOpenToAnyRoll()).isTrue();
        assertThat(frameToBeTested.isSpare()).isTrue();

    }

    @Test
    public void shouldHandleAddingANewLegalStrikeRoll(){

        //given
        RollEntity firstRoll = new RollEntity(10);

        //when
        frameToBeTested.handleAddingNewRoll(firstRoll);
        int expected = 10;

        //then
        assertThat(frameToBeTested.calculateFrameRollsValue()).isEqualTo(expected);
        assertThat(frameToBeTested.isFrameOpenToAnyRoll()).isTrue();
        assertThat(frameToBeTested.isStrike()).isTrue();

    }

    @Test
    public void shouldHandleAddingANewIllegalRoll(){

        //given
        RollEntity firstRoll = new RollEntity(5);
        RollEntity secondRoll = new RollEntity(7);
        frameToBeTested.getFrameRolls().add(firstRoll);

        //when
        IllegalSumOfRollsInFrameException illegalThrowException = assertThrows(IllegalSumOfRollsInFrameException.class, ()->frameToBeTested.handleAddingNewRoll(secondRoll));
        int expected = 5;

        //then
        assertTrue(illegalThrowException.getMessage().contains("Illegal throw: there are only 5  pin/s remaining"));
        assertThat(frameToBeTested.calculateFrameRollsValue()).isEqualTo(expected);

    }

}
