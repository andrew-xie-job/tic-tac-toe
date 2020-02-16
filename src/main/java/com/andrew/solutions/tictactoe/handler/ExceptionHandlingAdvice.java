package com.andrew.solutions.tictactoe.handler;

import com.andrew.solutions.tictactoe.exceptions.InvalidCoordinationException;
import com.andrew.solutions.tictactoe.exceptions.InvalidGameException;
import com.andrew.solutions.tictactoe.exceptions.InvalidUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAdvice.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<Object> handleIllegalArgumentException(Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = InvalidCoordinationException.class)
    ResponseEntity<Object> handleInvalidCoordinationException(Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidGameException.class)
    ResponseEntity<Object> handleInvalidGameException(Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidUserException.class)
    ResponseEntity<Object> handleInvalidUserException(Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
