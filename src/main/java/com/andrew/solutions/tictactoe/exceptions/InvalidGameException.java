package com.andrew.solutions.tictactoe.exceptions;

public class InvalidGameException extends RuntimeException {
    public InvalidGameException(String message) {
        super(message);
    }
}
