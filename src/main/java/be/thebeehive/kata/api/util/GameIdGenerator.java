package be.thebeehive.kata.api.util;
import java.util.UUID;

public class GameIdGenerator {

    public static String generateGameId(){

        return UUID.randomUUID().toString();

    }

}
