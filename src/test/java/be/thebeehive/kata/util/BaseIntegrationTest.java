package be.thebeehive.kata.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {
    protected static final Faker FAKER = Faker.instance();

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    protected String setupGame() {
        return deserialize(
                mockMvc.perform(
                                post("/bowling")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(new CreateGameDto(FAKER.starTrek().character())))
                        ).andReturn()
                        .getResponse()
                        .getContentAsString(),
                BowlingGameDto.class
        ).gameId();
    }

    @SneakyThrows
    protected BowlingGameDto consecutiveRolls(String gameId, int... rolls) {
        BowlingGameDto dto = null;

        for (int roll : rolls) {
            dto = roll(gameId, roll);
        }

        return dto;
    }

    @SneakyThrows
    private BowlingGameDto roll(String gameId, int pins) {
        return deserialize(mockMvc.perform(
                                post(String.format("/bowling/%s", gameId))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(serialize(new RollDto(pins)))
                        ).andReturn()
                        .getResponse()
                        .getContentAsString(),
                BowlingGameDto.class
        );
    }

    protected <T> String serialize(T object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    protected <T> T deserialize(String json, Class<T> tClass) throws Exception {
        return objectMapper.readValue(json, tClass);
    }
}
