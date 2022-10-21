package be.thebeehive.kata.api.errorhandling.exception;

public class GameOverException extends RuntimeException{
    public GameOverException(String message){
        super(message);
    }
}
