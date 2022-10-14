package be.thebeehive.kata.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;

import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.util.BaseIntegrationTest;

class RegularScoringRollIT extends BaseIntegrationTest {
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = setupGame();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void singleRollAddsResultToScore(int pins) throws Exception {
        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(pins)))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(pins));
    }

    @Test
    void consecutiveRollsGiveExpectedTotalScore() {
        int[] rolls = {0,4,6,2,7,2};
        int expectedScore = Arrays.stream(rolls).sum();

        var gameDto = consecutiveRolls(gameId, rolls);

        assertThat(gameDto.score()).isEqualTo(expectedScore);
    }
}
