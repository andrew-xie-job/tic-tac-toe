package com.andrew.solutions.tictactoe.dto;

import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;

import java.io.Serializable;

public class ResponseMoveDTO implements Serializable {
    private int boardColumn;
    private int boardRow;
    private String userName;
    private GameStatus gameStatus;
    private Piece playerPieceCode;

    public int getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(int boardColumn) {
        this.boardColumn = boardColumn;
    }

    public int getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(int boardRow) {
        this.boardRow = boardRow;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Piece getPlayerPieceCode() {
        return playerPieceCode;
    }

    public void setPlayerPieceCode(Piece playerPieceCode) {
        this.playerPieceCode = playerPieceCode;
    }
}
