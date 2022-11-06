package be.thebeehive.kata.api.controller;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.entities.BowlingGameEntity;
import be.thebeehive.kata.api.service.BowlingGameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class BowlingGameController {
    private final BowlingGameService bowlingGameService;

    @PostMapping(value = "/bowling")
    public BowlingGameDto createBowlingGame(@Valid @RequestBody CreateGameDto createGameDto) {

        log.info("Creating new game with name {}", createGameDto.name());
        return bowlingGameService.createBowlingGame(createGameDto);

    }

    @PostMapping(value = "/bowling/{gameId}")
    public BowlingGameDto performBowlingRoll(@PathVariable String gameId, @Valid @RequestBody RollDto rollDto) {

        log.info("Performing new roll with {} pins", rollDto.pins());
        return bowlingGameService.performBowlingRoll(gameId, rollDto);

     }

   // @PutMapping(value = "/bowling/{gameId}")
   // public BowlingGameDto updateGameName(@PathVariable String gameId, @Valid @RequestBody UpdateGameDto updateGameDto){

       // log.info("Changing game name with id {} to {} ", gameId, updateGameDto.name());
       // return bowlingGameService.updateGameName(gameId, updateGameDto.name());

   // }

 }
