package be.thebeehive.kata.api.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameIdGenerator {

    public String generateGameId(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
