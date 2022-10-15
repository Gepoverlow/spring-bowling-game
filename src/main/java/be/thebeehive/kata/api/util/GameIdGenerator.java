package be.thebeehive.kata.api.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameIdGenerator {

    public UUID generateGameId(){
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

}
