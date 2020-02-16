package com.andrew.solutions.tictactoe.domain.enums;

public enum Piece {
    X,
    O;
    private static Piece[] vals = values();

    public Piece next() {
        return vals[(this.ordinal()+1) % vals.length];
    }
}
