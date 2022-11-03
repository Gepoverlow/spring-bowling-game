package be.thebeehive.kata.api.mapper;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.entities.BowlingGameEntity;
import be.thebeehive.kata.api.entities.RollEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BowlingGameMapper {

    @Mapping(target = "score", constant = "0")
   // @Mapping(target = "gameId", expression = "java( UUID.randomUUID().toString() )")
    BowlingGameEntity createGameDtoToBowlingGameEntity(CreateGameDto createGameDto);

    BowlingGameDto bowlingGameEntityToDto(BowlingGameEntity bowlingGameEntity);

    RollEntity rollDtoToRollEntity(RollDto rollDto);

}
