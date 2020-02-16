package com.andrew.solutions.tictactoe.dto;


import java.io.Serializable;

public class RequestMoveDTO implements Serializable {
    private int boardColumn;
    private int boardRow;

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
}
