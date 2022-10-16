package be.thebeehive.kata.api.dto;

import javax.validation.constraints.NotBlank;


public record CreateGameDto(

        @NotBlank
        String name

) {
}
