package be.thebeehive.kata.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import be.thebeehive.kata.util.BaseIntegrationTest;

class SpareScoringIT extends BaseIntegrationTest {
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = setupGame();
    }

    @ParameterizedTest
    @MethodSource("singleSpareSource")
    void firstNextRollCountsDoubleWhenRollingASpare(int[] rolls, int expectedScore) {
        var game = consecutiveRolls(gameId, rolls);

        assertThat(game.score()).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> singleSpareSource() {
        return Stream.of(
                Arguments.of(new int[]{0, 10, 0}, 10),
                Arguments.of(new int[]{0, 10, 1}, 12),
                Arguments.of(new int[]{0, 10, 2}, 14),
                Arguments.of(new int[]{0, 10, 5}, 20),
                Arguments.of(new int[]{0, 10, 1, 1}, 13)
        );
    }
}
