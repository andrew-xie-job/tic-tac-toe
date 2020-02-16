package com.andrew.solutions.tictactoe.domain;

import com.andrew.solutions.tictactoe.domain.enums.Column;
import com.andrew.solutions.tictactoe.domain.enums.Row;

import java.util.Objects;

public class Coordination {
    private final Row row;
    private final Column column;

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }

    public Coordination(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordination that = (Coordination) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
