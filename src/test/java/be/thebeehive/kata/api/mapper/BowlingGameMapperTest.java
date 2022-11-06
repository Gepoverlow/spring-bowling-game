package be.thebeehive.kata.api.mapper;

import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.entities.BowlingGameEntity;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BowlingGameMapperImpl.class)
public class BowlingGameMapperTest {

    private static final Faker FAKER = Faker.instance();

    @Autowired
    BowlingGameMapper bowlingGameMapper;

    @Test
    public void shouldMapCreateDtoToModel(){

        String name = FAKER.starTrek().character();
        CreateGameDto createGameDto = new CreateGameDto(name);
        BowlingGameEntity bowlingGameEntity = bowlingGameMapper.createGameDtoToBowlingGameEntity(createGameDto);
        assertThat(bowlingGameEntity.getName()).isEqualTo(name);
        assertThat(bowlingGameEntity.getScore()).isEqualTo(0);
        assertThat(bowlingGameEntity.getGameId()).isNotNull();

    }

}
