package com.andrew.solutions.tictactoe.service;

import com.andrew.solutions.tictactoe.domain.Coordination;
import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;
import com.andrew.solutions.tictactoe.exceptions.InvalidCoordinationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.andrew.solutions.tictactoe.domain.enums.Column.*;
import static com.andrew.solutions.tictactoe.domain.enums.Row.*;
import static java.util.Arrays.asList;

@Service
public class MoveService {

    private static final int FULL = 9;
    private static final int CHECKING_POINT = 4;
    private static List<List<Coordination>> winingLists = new ArrayList<>();

    static {
        winingLists.add(asList(new Coordination(R1, C1), new Coordination(R1, C2), new Coordination(R1, C3)));
        winingLists.add(asList(new Coordination(R2, C1), new Coordination(R2, C2), new Coordination(R2, C3)));
        winingLists.add(asList(new Coordination(R3, C1), new Coordination(R3, C2), new Coordination(R3, C3)));

        winingLists.add(asList(new Coordination(R1, C1), new Coordination(R2, C1), new Coordination(R3, C1)));
        winingLists.add(asList(new Coordination(R1, C2), new Coordination(R2, C2), new Coordination(R3, C2)));
        winingLists.add(asList(new Coordination(R1, C3), new Coordination(R2, C3), new Coordination(R3, C3)));

        winingLists.add(asList(new Coordination(R1, C1), new Coordination(R2, C2), new Coordination(R3, C3)));
        winingLists.add(asList(new Coordination(R3, C1), new Coordination(R2, C2), new Coordination(R1, C3)));
    }

    public GameStatus createMove(Game game, Coordination coordination) {
        if(game.getStatus() != GameStatus.IN_PROGRESS)
            return game.getStatus();
        verifyCoordination(game, coordination);
        game.saveMove(coordination);
        GameStatus gameStatus = checkGameStatus(game);
        if(gameStatus != GameStatus.IN_PROGRESS) {
            game.endGame(gameStatus);
        } else {
            game.next();
        }
        return game.getStatus();
    }


    public GameStatus checkGameStatus(Game game) {
        if(game.getMoves().size() > CHECKING_POINT) { //no winner before 5 moves
            if(checkWinner(getPlayerMoves(game, game.getCurrentPiece()))) {
                if (game.getCurrentPiece() == Piece.X) {
                    return GameStatus.X_PLAYER_WON;
                }
                else return GameStatus.O_PLAYER_WON;
            }
        }
        if (isBoardFull(game)){
            return GameStatus.TIE;
        }
        return game.getStatus();
    }

    private boolean checkWinner(List<Coordination> moves) {
            return winingLists.stream().anyMatch(moves::containsAll);
    }

    private void verifyCoordination(Game game, Coordination coordination) {
        if(game.getMoves().contains(coordination)) {
            throw new InvalidCoordinationException("Coordination: ["
                    + coordination.getRow()
                    + ", "
                    + coordination.getColumn()
                    + "] is not empty");
        }
    }

    public List<Coordination> getPlayerMoves(Game game, Piece piece) {
        List<Coordination> moves = game.getMoves();
        List<Coordination> playerMoves = new ArrayList<>();
        if (Piece.X == piece) {
            for(int i = 0; i< moves.size(); i += 2) {
                playerMoves.add(moves.get(i));
            }
        } else {
            for(int i = 1; i< moves.size(); i += 2) {
                playerMoves.add(moves.get(i));
            }
        }
        return playerMoves;
    }

    private boolean isBoardFull(Game game) {
        return game.getMoves().size() == FULL;
    }

    public List<Coordination> getMovesInGame(Game game) {
        return game.getMoves();
    }
}
