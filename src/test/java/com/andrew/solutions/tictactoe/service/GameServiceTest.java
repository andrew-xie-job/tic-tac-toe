package com.andrew.solutions.tictactoe.service;

import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameServiceTest {

    private static final String USER_NAME = "userName";
    private static final String USER_NAME_1 = "userName1";
    private static final String PASSWORD = "password";

    private GameService gameService;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        gameService = new GameService();
        player1 = new Player(USER_NAME, PASSWORD);
        player2 = new Player(USER_NAME_1, PASSWORD);
    }

    @Test
    public void will_CreateGame() {

        Game game = gameService.createGame(player1);
        assertThat(game.getXPlayer().getUserName()).isEqualTo(USER_NAME); // game creator is the x player.
        assertThat(game.getStatus()).isEqualTo(GameStatus.WAITING);
        assertThat(game.getCurrentPiece()).isEqualTo(Piece.X); //X starts first
    }

    @Test
    public void will_JoinGame() {
        Game game = gameService.createGame(player1);
        gameService.joinGame(player2, game.getId());
        assertThat(game.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(game.getOPlayer()).isEqualTo(player2);
        assertThat(game.getXPlayer()).isEqualTo(player1);
    }

    @Test
    public void will_returnGameById() {
        Game game = gameService.createGame(player1);
        long gameId = game.getId();
        assertThat(gameService.getGameById(gameId).get()).isEqualToComparingFieldByField(game);
        assertThat(gameService.getGameById(-1L)).isNotPresent();
    }

    @Test
    public void will_returnAllGames() {
        Game game1 = gameService.createGame(player1);
        gameService.createGame(player1);
        gameService.createGame(player1);
        assertThat(gameService.getAllGames().size()).isEqualTo(3);
        assertThat(gameService.getAllGames()).contains(game1);
    }

    @Test
    public void will_returnAllWaitingGames() {
        Game game1 = gameService.createGame(player1);
        Game game2 = gameService.createGame(player1);
        Game game3 = gameService.createGame(player1);

        game2.endGame(GameStatus.TIE);
        assertThat(gameService.getAllWaitingGames().size()).isEqualTo(2);
        assertThat(gameService.getAllWaitingGames()).contains(game1);
        assertThat(gameService.getAllWaitingGames()).contains(game3);
        assertThat(gameService.getAllWaitingGames()).doesNotContain(game2);
    }
}
