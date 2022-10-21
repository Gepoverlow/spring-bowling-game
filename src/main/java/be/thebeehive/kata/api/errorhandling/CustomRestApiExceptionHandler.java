package be.thebeehive.kata.api.errorhandling;

import be.thebeehive.kata.api.errorhandling.exception.BowlingGameNotFoundException;
import be.thebeehive.kata.api.errorhandling.exception.GameOverException;
import be.thebeehive.kata.api.errorhandling.exception.IllegalSumOfRollsInFrameException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class CustomRestApiExceptionHandler extends ResponseEntityExceptionHandler {


     @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Invalid request. Verify all required data is included");

        return new ResponseEntity(apiError, headers, status);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

         List<String> errors = new ArrayList<String>();

         for (FieldError error : ex.getBindingResult().getFieldErrors()){
             errors.add(error.getField() + ":" + error.getDefaultMessage());
         }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ":" + error.getDefaultMessage());
        }


        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,errors.get(0));

        return new ResponseEntity(apiError, headers, status);

    }

    @ExceptionHandler({BowlingGameNotFoundException.class})
    protected ResponseEntity<Object> handleBowlingGameNotFound(BowlingGameNotFoundException ex){

        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({IllegalSumOfRollsInFrameException.class})
    protected ResponseEntity<Object> handleIllegalRoll(IllegalSumOfRollsInFrameException ex){

         final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

         return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({GameOverException.class})
    protected ResponseEntity<Object> handleGameOver(GameOverException ex){

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);

    }

}
