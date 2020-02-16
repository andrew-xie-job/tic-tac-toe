package com.andrew.solutions.tictactoe.controller;

import com.andrew.solutions.tictactoe.domain.Coordination;
import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.enums.Column;
import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;
import com.andrew.solutions.tictactoe.domain.enums.Row;
import com.andrew.solutions.tictactoe.dto.RequestMoveDTO;
import com.andrew.solutions.tictactoe.exceptions.InvalidGameException;
import com.andrew.solutions.tictactoe.service.GameService;
import com.andrew.solutions.tictactoe.service.MoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/tictactoe/api/v1/move")
public class MoveController {
    Logger LOGGER = LoggerFactory.getLogger(MoveController.class);

    private final MoveService moveService;
    private final GameService gameService;
    private final HttpSession httpSession;

    @Autowired
    public MoveController(MoveService moveService,
                          GameService gameService,
                          HttpSession httpSession) {
        this.moveService = moveService;
        this.gameService = gameService;
        this.httpSession = httpSession;
    }

    @RequestMapping(value = "/play", method = RequestMethod.POST)
    public GameStatus createMove(@RequestBody RequestMoveDTO requestMoveDTO) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Row row = Row.from(requestMoveDTO.getBoardRow());
        Column column = Column.from(requestMoveDTO.getBoardColumn());
        LOGGER.info("move to insert: [{},{}]", row, column);

        Coordination coordination = new Coordination(row, column);
        Game game = gameService.getGameById(gameId)
                .orElseThrow(()-> new InvalidGameException("The GameId: " + gameId + " cannot be found"));
        return moveService.createMove(game, coordination);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Coordination> getPlayers() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId)
                .orElseThrow(()-> new InvalidGameException("The GameId: " + gameId + " cannot be found"));
        return moveService.getMovesInGame(game);
    }

    @RequestMapping(value = "/player/{piece}", method = RequestMethod.GET)
    public List<Coordination> getPlayerMoves(@PathVariable Piece piece) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId)
                .orElseThrow(()-> new InvalidGameException("The GameId: " + gameId + " cannot be found"));
        return moveService.getPlayerMoves(game, piece);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public GameStatus getGameStatus() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId)
                .orElseThrow(()-> new InvalidGameException("The GameId: " + gameId + " cannot be found"));
        return moveService.checkGameStatus(game);
    }

}
