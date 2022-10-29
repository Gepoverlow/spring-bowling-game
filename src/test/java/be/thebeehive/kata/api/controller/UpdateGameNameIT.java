package be.thebeehive.kata.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.UpdateGameDto;
import be.thebeehive.kata.util.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    //TODO: set up method that tests for empty / bad requests on update game name

}
