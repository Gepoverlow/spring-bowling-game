package be.thebeehive.kata.api.controller;

import be.thebeehive.kata.api.dto.BowlingGameDto;
import be.thebeehive.kata.api.dto.CreateGameDto;
import be.thebeehive.kata.api.service.BowlingGameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class BowlingGameController {
    private final BowlingGameService bowlingGameService;

    @PostMapping(value = "/bowling")
    public ResponseEntity<BowlingGameDto> createBowlingGame(@Valid @RequestBody CreateGameDto createGameDto){

        return ResponseEntity.ok().body(bowlingGameService.createBowlingGame(createGameDto));

    }

}
