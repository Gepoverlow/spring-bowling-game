package be.thebeehive.kata.api.dto;

public record BowlingGameDto(
        String gameId,
        String name,
        int score
) {
}
