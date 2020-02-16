package com.andrew.solutions.tictactoe.controller;

import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.dto.GameDTO;
import com.andrew.solutions.tictactoe.exceptions.InvalidGameException;
import com.andrew.solutions.tictactoe.exceptions.InvalidUserException;
import com.andrew.solutions.tictactoe.service.GameService;
import com.andrew.solutions.tictactoe.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/tictactoe/api/v1/game")
public class GameController {
    private static Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private final PlayerService playerService;
    private HttpSession httpSession;

    public GameController(GameService gameService, PlayerService playerService, HttpSession httpSession) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.httpSession = httpSession;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createGame() {
        Player player = playerService.getLoggedUser().orElseThrow(() -> new InvalidUserException("Cannot find user"));
        Game game = gameService.createGame(player);
        httpSession.setAttribute("gameId", game.getId());
        logger.info("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );
        return game;
    }

    @RequestMapping(value = "/join/list", method = RequestMethod.GET)
    public List<Game> getGamesToJoin() {
        return gameService.getAllWaitingGames();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Game joinGame(@RequestBody GameDTO gameDTO) {
        Player player = playerService.getLoggedUser().orElseThrow(() -> new InvalidUserException("Cannot find user"));
        return gameService.joinGame(player, gameDTO.getId());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game getGameById(@PathVariable Long id) {
        httpSession.setAttribute("gameId", id);
        return gameService.getGameById(id)
                .orElseThrow(() -> new InvalidGameException("The GameId: " + id + " cannot be found"));
    }
}
