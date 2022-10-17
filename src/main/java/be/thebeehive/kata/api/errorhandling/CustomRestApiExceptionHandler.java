package be.thebeehive.kata.api.errorhandling;

import be.thebeehive.kata.api.errorhandling.exception.BowlingGameNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomRestApiExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Invalid request. Verify all required data is included");

        return new ResponseEntity(apiError, headers, status);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"name:\tName is required");

        return new ResponseEntity(apiError, headers, status);

    }

    @ExceptionHandler({BowlingGameNotFoundException.class})
    protected ResponseEntity<Object> handleBowlingGameNotFound(BowlingGameNotFoundException ex){

        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);

    }





}
