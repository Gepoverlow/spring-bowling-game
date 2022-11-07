package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 public class BowlingGameServiceTest {

    @Autowired
    BowlingGameService bowlingGameService;

    @Test
    public void shouldCreateGameSuccessfully(){

        // given - precondition or setup
        CreateGameDto createGameDto = new CreateGameDto("Testing-Game-Name");

       // when - action or the behaviour that we are going test
        BowlingGameDto bowlingGameDto = bowlingGameService.createBowlingGame(createGameDto);

       // then - verify the output
        assertThat(bowlingGameDto).isNotNull();
        assertThat(bowlingGameDto.gameId()).isNotNull();

    }

}
