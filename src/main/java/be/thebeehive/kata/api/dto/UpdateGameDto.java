package be.thebeehive.kata.api.dto;

import javax.validation.constraints.NotBlank;


public record UpdateGameDto(

        @NotBlank(message = "\tName is required to update the game name")
        String name

) {
}
