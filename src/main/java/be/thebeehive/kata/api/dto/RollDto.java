package be.thebeehive.kata.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public record RollDto(

        @Min(value = 0, message = "\tInvalid number of pins")
        @Max(value = 10, message = "\tInvalid number of pins")
        int pins
) {
}
