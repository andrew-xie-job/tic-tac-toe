package com.andrew.solutions.tictactoe.domain.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    private Piece piece = Piece.X;

    @Test
    void will_GetNext() {
        assertThat(piece.next()).isEqualTo(Piece.O);
        assertThat(piece.next().next()).isEqualTo(Piece.X);
    }
}
