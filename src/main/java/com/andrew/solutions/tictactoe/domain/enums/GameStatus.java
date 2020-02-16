package com.andrew.solutions.tictactoe.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameStatus {
    WAITING("WAITING"),
    IN_PROGRESS("IN_PROGRESS"),
    X_PLAYER_WON("X_PLAYER_WON"),
    O_PLAYER_WON("O_PLAYER_WON"),
    TIE("TIE");

    private String gameStatus;

    GameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
