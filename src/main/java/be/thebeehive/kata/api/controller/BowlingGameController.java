package be.thebeehive.kata.api.controller;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.dto.RollDto;
import be.thebeehive.kata.api.service.BowlingGameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class BowlingGameController {
    private final BowlingGameService bowlingGameService;

    @PostMapping(value = "/bowling")
    public ResponseEntity<BowlingGameDto> createBowlingGame(@Valid @RequestBody CreateGameDto createGameDto) {

        BowlingGameDto createdBowlingGame = bowlingGameService.createBowlingGame(createGameDto);

        return ResponseEntity.ok().body(bowlingGameService.createBowlingGame(createGameDto));

    }

    @PostMapping(value = "/bowling/{gameId}")
    public ResponseEntity<BowlingGameDto> performBowlingRoll(@PathVariable String gameId, @Valid @RequestBody RollDto rollDto) {

        return ResponseEntity.ok().body(bowlingGameService.performBowlingRoll(gameId, rollDto));

    }

}
