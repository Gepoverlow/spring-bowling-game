package be.thebeehive.kata.api.service;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.mapper.BowlingGameMapper;
import be.thebeehive.kata.api.mapper.BowlingGameMapperImpl;
import be.thebeehive.kata.api.model.BowlingGameModel;
import be.thebeehive.kata.api.repository.BowlingGameRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//TODO: this doesnt work and have been a long time trying to figure it out, coming back to it later
public class BowlingGameServiceTest {

    @Mock
    BowlingGameRepository bowlingGameRepository;
    @Mock
    BowlingGameMapper bowlingGameMapper;
    @InjectMocks
    BowlingGameService bowlingGameService;

    @Test
    public void shouldCreateGameSuccessfully(){

        //given - precondition or setup
        CreateGameDto createGameDto = new CreateGameDto("Testing-Game-Name");
        //TODO: Why does this bowlingGameModel return null? is the injection of the mapper class done wrong?
        BowlingGameModel bowlingGameModel = bowlingGameMapper.createGameDtoToBowlingGameModel(createGameDto);

        when(bowlingGameRepository.addNewBowlingGame(any(BowlingGameModel.class))).thenReturn(new BowlingGameDto(bowlingGameModel.getGameId(), bowlingGameModel.getName(), bowlingGameModel.getScore()));

        //when - action or the behaviour that we are going test
        BowlingGameDto bowlingGameDto = bowlingGameService.createBowlingGame(createGameDto);

        //then - verify the output
        assertThat(bowlingGameDto).isNotNull();

    }

}
