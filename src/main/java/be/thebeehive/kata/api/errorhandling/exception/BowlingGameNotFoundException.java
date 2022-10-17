package be.thebeehive.kata.api.errorhandling.exception;

public class BowlingGameNotFoundException extends RuntimeException{
    public BowlingGameNotFoundException(String message){
        super(message);
    }
}
