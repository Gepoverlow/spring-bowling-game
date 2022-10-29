package be.thebeehive.kata.api.mapper;

import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.model.BowlingGameModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)

public interface BowlingGameMapper {

    @Mapping(target = "score", constant = "0")
    @Mapping(target = "gameId", expression = "java( UUID.randomUUID().toString() )")
    BowlingGameModel createGameDtoToBowlingGameModel(CreateGameDto createGameDto);

}
