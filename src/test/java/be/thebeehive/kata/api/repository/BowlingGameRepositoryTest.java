package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.entities.BowlingGameEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BowlingGameRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BowlingGameRepository bowlingGameRepository;

    @Test
    public void shouldReturnBowlingGameEntityOnSave(){

        //given
        BowlingGameEntity newBowlingGameEntity = new BowlingGameEntity();
        newBowlingGameEntity.setName("Test-Game");
        newBowlingGameEntity.setScore(0);

        //when
        BowlingGameEntity savedBowlingGameEntity = bowlingGameRepository.save(newBowlingGameEntity);

        //then
        assertThat(savedBowlingGameEntity).isNotNull();

    }

    @Test
    public void shouldFindBowlingGameWithGivenGameId(){

        //given
        BowlingGameEntity newBowlingGameEntity = new BowlingGameEntity();
        newBowlingGameEntity.setName("Test-Game");
        newBowlingGameEntity.setScore(0);
        testEntityManager.persist(newBowlingGameEntity);
        testEntityManager.flush();

        //when
        Optional<BowlingGameEntity> foundBowlingGameOpt = bowlingGameRepository.findById(newBowlingGameEntity.getGameId());

        //then
        assertThat(newBowlingGameEntity.getGameId()).isNotNull();
        assertThat(foundBowlingGameOpt).isNotNull();

    }

}
