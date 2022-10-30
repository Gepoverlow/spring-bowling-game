package be.thebeehive.kata.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.UpdateGameDto;
import be.thebeehive.kata.util.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateGameNameIT extends BaseIntegrationTest {

    private String gameId;

    @BeforeEach
     void setUp(){gameId = setupGame();}

    @Test
    void updateGameNameWithValidUpdateGameDtoShouldSucceed() throws Exception {

        String newName = FAKER.starTrek().character();
        UpdateGameDto updateGameDto = new UpdateGameDto(newName);
        BowlingGameDto expected = new BowlingGameDto(null, newName, 0);

        MockHttpServletResponse response = mockMvc.perform(
                put(String.format("/bowling/%s", gameId))
                        .content(serialize(updateGameDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse();

        BowlingGameDto result = deserialize(response.getContentAsString(), BowlingGameDto.class);
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("gameId")
                .isEqualTo(expected);

        assertThat(result.gameId()).isNotBlank();

    }

    @Test
    void updateGameWithMissingBodyShouldReturnBadRequestWithExpectedMessage() throws Exception {
        mockMvc.perform(
                        put(String.format("/bowling/%s", gameId))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request. Verify all required data is included"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void updateGameWithEmptyNameShouldReturnBadRequestWithExpectedMessage(String name) throws Exception {
        UpdateGameDto updateGameDto = new UpdateGameDto(name);

        mockMvc.perform(
                        put(String.format("/bowling/%s", gameId))
                                .content(serialize(updateGameDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("name:\tName is required to update the game name"));
    }

}
