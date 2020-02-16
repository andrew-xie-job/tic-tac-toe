package com.andrew.solutions.tictactoe.service;

import com.andrew.solutions.tictactoe.domain.Coordination;
import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;
import com.andrew.solutions.tictactoe.exceptions.InvalidCoordinationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.andrew.solutions.tictactoe.domain.enums.Column.*;
import static com.andrew.solutions.tictactoe.domain.enums.Row.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MoveServiceTest {

    private static final String USER_NAME = "userName";
    private static final String USER_NAME_1 = "userName1";
    private static final String PASSWORD = "password";
    private static final long GAME_ID = 1L;

    private Game game;
    private Player player1;
    private Player player2;
    private MoveService moveService;

    @BeforeEach
    public void setUp() {
        moveService = new MoveService();
        player1 = new Player(USER_NAME, PASSWORD);
        player2 = new Player(USER_NAME_1, PASSWORD);
        game = new Game(GAME_ID, player1);
    }

    @Test
    public void Will_CreateMove_WhenStatusIsNotWaiting() {
        game.joinGame(player2);
        Coordination coordination = new Coordination(R1, C2);
        GameStatus gameStatus = moveService.createMove(game, coordination);
        assertThat(gameStatus).isNotEqualTo(GameStatus.WAITING);
        assertThat(moveService.getMovesInGame(game).size()).isEqualTo(1);
    }

    @Test
    public void WillNot_CreateMove_WhenStatusIsWaiting() {
        Coordination coordination = new Coordination(R1, C2);
        GameStatus gameStatus = moveService.createMove(game, coordination);
        assertThat(gameStatus).isEqualTo(GameStatus.WAITING);
        assertThat(moveService.getMovesInGame(game).size()).isZero();
    }

    @Test
    public void Will_CreateMove_AndEndGame_WithWinner() {
        game.joinGame(player2);
        moveService.createMove(game, new Coordination(R1, C1));
        moveService.createMove(game, new Coordination(R2, C2));
        moveService.createMove(game, new Coordination(R1, C2));
        moveService.createMove(game, new Coordination(R2, C1));
        moveService.createMove(game, new Coordination(R1, C3));

        assertThat(game.getStatus()).isEqualTo(GameStatus.X_PLAYER_WON);

        assertThat(moveService.getMovesInGame(game).size()).isEqualTo(5);
        assertThat(moveService.getPlayerMoves(game, Piece.X).size()).isEqualTo(3);
        assertThat(moveService.getPlayerMoves(game, Piece.O).size()).isEqualTo(2);
    }

    @Test
    public void Will_CreateMove_AndEndGame_WithTie() {
        game.joinGame(player2);
        moveService.createMove(game, new Coordination(R1, C1));
        moveService.createMove(game, new Coordination(R1, C2));
        moveService.createMove(game, new Coordination(R2, C1));
        moveService.createMove(game, new Coordination(R2, C2));
        moveService.createMove(game, new Coordination(R1, C3));
        moveService.createMove(game, new Coordination(R3, C1));
        moveService.createMove(game, new Coordination(R3, C2));
        moveService.createMove(game, new Coordination(R3, C3));
        moveService.createMove(game, new Coordination(R2, C3));

        assertThat(game.getStatus()).isEqualTo(GameStatus.TIE);

        assertThat(moveService.getMovesInGame(game).size()).isEqualTo(9);
        assertThat(moveService.getPlayerMoves(game, Piece.X).size()).isEqualTo(5);
        assertThat(moveService.getPlayerMoves(game, Piece.O).size()).isEqualTo(4);
    }


    @Test
    public void WillThrowException_WhenMoveToInvalidPosition() {
        Coordination coordination = new Coordination(R1, C2);
        game.joinGame(player2);
        moveService.createMove(game, coordination);
        Throwable exception = assertThrows(InvalidCoordinationException.class,
                () -> moveService.createMove(game, coordination));
        assertTrue(exception.getMessage().contains("is not empty"));
    }

    @Test
    public void checkGameStatus() {
        assertThat(moveService.checkGameStatus(game)).isEqualTo(GameStatus.WAITING);

        game.joinGame(player2);
        assertThat(moveService.checkGameStatus(game)).isEqualTo(GameStatus.IN_PROGRESS);

        moveService.createMove(game, new Coordination(R1, C1));
        assertThat(moveService.checkGameStatus(game)).isEqualTo(GameStatus.IN_PROGRESS);

        moveService.createMove(game, new Coordination(R1, C3));
        assertThat(moveService.checkGameStatus(game)).isEqualTo(GameStatus.IN_PROGRESS);

        moveService.createMove(game, new Coordination(R1, C2));
        moveService.createMove(game, new Coordination(R2, C2));
        moveService.createMove(game, new Coordination(R2, C1));
        moveService.createMove(game, new Coordination(R3, C1));
        assertThat(moveService.checkGameStatus(game)).isEqualTo(GameStatus.O_PLAYER_WON);
    }

}
