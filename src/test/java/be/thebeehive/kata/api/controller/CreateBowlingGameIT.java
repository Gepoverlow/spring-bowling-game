package be.thebeehive.kata.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.util.BaseIntegrationTest;

@SpringBootTest
@AutoConfigureMockMvc
class CreateBowlingGameIT extends BaseIntegrationTest {
    @Test
    void createGameWithValidCreateGameDtoShouldSucceed() throws Exception {
        String name = FAKER.starTrek().character();
        CreateGameDto createGameDto = new CreateGameDto(name);
        BowlingGameDto expected = new BowlingGameDto(null, name, 0);

        MockHttpServletResponse response = mockMvc.perform(
                        post("/bowling")
                                .content(serialize(createGameDto))
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
    void createGameWithMissingBodyShouldReturnBadRequestWithExpectedMessage() throws Exception {
        mockMvc.perform(
                        post("/bowling")
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request. Verify all required data is included"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void createGameWithEmtpyNameShouldReturnBadRequestWithExpectedMessage(String name) throws Exception {
        CreateGameDto createGameDto = new CreateGameDto(name);

        mockMvc.perform(
                        post("/bowling")
                                .content(serialize(createGameDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("name:\tName is required"));
    }
}
