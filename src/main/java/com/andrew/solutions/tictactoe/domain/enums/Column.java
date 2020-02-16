package com.andrew.solutions.tictactoe.domain.enums;

import com.andrew.solutions.tictactoe.exceptions.InvalidValueRangeException;

public enum Column {
    C1(0),
    C2(1),
    C3(2);

    private int value;
    Column(final int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public static Column from(int value) {
        for (Column col : Column.values()) {
            if (value == col.get()) {
                return col;
            }
        }
        throw new InvalidValueRangeException("Invalid value:" + value + ", column value range is [0,2].");
    }
}

