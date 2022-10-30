package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.model.BowlingGameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BowlingGameRepositoryTest {

    @Autowired
    BowlingGameRepository bowlingGameRepository;

    private BowlingGameModel bowlingGameModel;

    @BeforeEach
    public void setUp(){

        bowlingGameModel = new BowlingGameModel();
        bowlingGameModel.setName("Testing-Game-Name");
        bowlingGameModel.setGameId("aze-aze-aze-123");
        bowlingGameModel.setScore(0);

    }

    @Test
    public void shouldAddNewGameSuccessfullyAndReturnBowlingGameDto(){

        BowlingGameDto gameDto = bowlingGameRepository.addNewBowlingGame(bowlingGameModel);
        assertThat(gameDto).isNotNull();

    }

    @Test
    public void shouldFindBowlingGameWithValidGameId(){

        bowlingGameRepository.addNewBowlingGame(bowlingGameModel);
        BowlingGameModel foundBowlingGameModel = bowlingGameRepository.findBowlingGameByGameId(bowlingGameModel.getGameId());
        assertEquals(bowlingGameModel.getName(), foundBowlingGameModel.getName());

    }

    //TODO: Test case for when the searched game does not exists and the GameNotFoundException is thrown


}
