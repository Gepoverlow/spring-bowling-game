package be.thebeehive.kata.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import be.thebeehive.kata.util.BaseIntegrationTest;

class StrikeScoringIT extends BaseIntegrationTest {
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = setupGame();
    }

    @ParameterizedTest
    @MethodSource("strikeSource")
    void aStrikeDoublesTheNextTwoRolls(int[] rolls, int expectedScore) {
        assertThat(consecutiveRolls(gameId, rolls).score()).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> strikeSource() {
        return Stream.of(
                Arguments.of(new int[]{10, 3, 5}, 26),
                Arguments.of(new int[]{10, 10, 3, 6}, 51),
                Arguments.of(new int[]{10, 10,10, 10,10, 10,10, 10,10, 10,10, 10}, 300)
        );
    }
}
