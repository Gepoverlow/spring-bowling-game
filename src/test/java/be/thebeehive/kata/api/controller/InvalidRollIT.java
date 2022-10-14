package be.thebeehive.kata.api.controller;

/**
 * Uncomment the comments when you start working on this
 */

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import be.thebeehive.kata.api.dto.RollDto;
//import be.thebeehive.kata.service.BowlingService;
import be.thebeehive.kata.util.BaseIntegrationTest;
import be.thebeehive.kata.util.ReflectionUtil;

class InvalidRollIT extends BaseIntegrationTest {
    public static final String GAME_FIELD = "game";

    private String gameId;

//    @Autowired
//    BowlingService service;

    @BeforeEach
    void setUp() {
        gameId = setupGame();
    }

    @Test
    void rollWithUnexistingGameIdReturnsExpectedWhenNoGameExists() throws Exception {
//        ReflectionUtil.clearField(service, GAME_FIELD);
        gameId = "u-".concat(UUID.randomUUID().toString());
        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(5)))
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("Game with id %s not found", gameId)));
    }

    @Test
    void rollWithUnexistingGameIdReturnsExpectedWhenGameExists() throws Exception {
        gameId = "u-".concat(UUID.randomUUID().toString());
        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(5)))
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("Game with id %s not found", gameId)));
    }

    @Test
    void rollWithNegativeNumberOfPinsReturnsExpectedException() throws Exception {
        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(-5)))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("pins:\tInvalid number of pins"));
    }

    @Test
    void rollWithExcessiveNumberOfPinsReturnsExpectedException() throws Exception {
        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(11)))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("pins:\tInvalid number of pins"));
    }

    @Test
    void zeroGameStopsAfterFrame10() throws Exception {
        consecutiveRolls(gameId, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(9)))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Game over. Final score is 0"));
    }

    @Test
    void perfectGameStopsAt300() throws Exception {
        consecutiveRolls(gameId, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);

        mockMvc.perform(
                        post(String.format("/bowling/%s", gameId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serialize(new RollDto(9)))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Game over. Final score is 300"));
    }
}
