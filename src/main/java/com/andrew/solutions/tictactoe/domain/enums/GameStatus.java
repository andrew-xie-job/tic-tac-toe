package com.andrew.solutions.tictactoe.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameStatus {
    WAITING("Waiting"),
    IN_PROGRESS("In-progress"),
    X_PLAYER_WON("X-player-won"),
    O_PLAYER_WON("O-player-won"),
    TIE("Tie");

    private String gameStatus;

    GameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
