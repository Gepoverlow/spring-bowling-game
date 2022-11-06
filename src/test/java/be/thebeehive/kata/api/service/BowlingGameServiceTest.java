package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.entities.BowlingGameEntity;
import be.thebeehive.kata.api.mapper.BowlingGameMapper;
import be.thebeehive.kata.api.repository.BowlingGameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@SpringBootTest

//TODO:::::::
 public class BowlingGameServiceTest {

    @Autowired
    BowlingGameMapper bowlingGameMapper;
    @Autowired
    BowlingGameRepository bowlingGameRepository;
    @Autowired
    BowlingGameService bowlingGameService;

    @Test
    public void shouldCreateGameSuccessfully(){

        // given - precondition or setup
        CreateGameDto createGameDto = new CreateGameDto("Testing-Game-Name");

        BowlingGameEntity bowlingGameEntity = bowlingGameMapper.createGameDtoToBowlingGameEntity(createGameDto);

        when(bowlingGameRepository.save(any(BowlingGameEntity.class))).thenReturn(bowlingGameEntity);

       // when - action or the behaviour that we are going test
        BowlingGameDto bowlingGameDto = bowlingGameService.createBowlingGame(createGameDto);

       // then - verify the output
        assertThat(bowlingGameDto).isNotNull();

    }

}
