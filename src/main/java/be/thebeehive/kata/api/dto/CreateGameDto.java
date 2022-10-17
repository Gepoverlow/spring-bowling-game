package be.thebeehive.kata.api.dto;

import javax.validation.constraints.NotBlank;


public record CreateGameDto(

        @NotBlank(message = "\tName is required")
        String name

) {
}
