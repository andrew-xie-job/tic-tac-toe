package com.andrew.solutions.tictactoe.domain.enums;

import com.andrew.solutions.tictactoe.exceptions.InvalidValueRangeException;

public enum Row {
    R1(0),
    R2(1),
    R3(2);

    private int value;

    Row(final int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public static Row from(int value) {
        for (Row row : Row.values()) {
            if (value == row.get()) {
                return row;
            }
        }
        throw new InvalidValueRangeException("Invalid value:" + value + ", row value range is [0,2].");
    }
}
