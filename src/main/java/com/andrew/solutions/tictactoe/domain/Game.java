package com.andrew.solutions.tictactoe.domain;

import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final long id;
    private final Player xPlayer;
    private Player oPlayer;
    private Piece currentPiece;
    private List<Coordination> moves = new ArrayList<>();
    private GameStatus status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Game(long id, Player xPlayer) {
        this.id = id;
        this.xPlayer = xPlayer;
        this.status = GameStatus.WAITING;
        this.currentPiece = Piece.X;
    }

    public void saveMove(Coordination coordination) {
        moves.add(coordination);
    }

    public void endGame(GameStatus gameStatus) {
        this.status = gameStatus;
        this.endDateTime = LocalDateTime.now();
    }

    public void joinGame(Player player) {
        this.oPlayer = player;
        this.status = GameStatus.IN_PROGRESS;
        this.startDateTime = LocalDateTime.now();
    }

    public Piece next() {
        currentPiece = currentPiece.next();
        return currentPiece;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public List<Coordination> getMoves() {
        return moves;
    }

    public GameStatus getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public Player getXPlayer() {
        return xPlayer;
    }

    public Player getOPlayer() {
        return oPlayer;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
