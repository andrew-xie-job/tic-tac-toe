package com.andrew.solutions.tictactoe.controller;

import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.dto.RequestPlayerDTO;
import com.andrew.solutions.tictactoe.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tictactoe/api/v1/player")
@Api(value="Player controlling API")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new user account")
    @ResponseStatus(HttpStatus.CREATED)
    public Player createAccount(@RequestBody RequestPlayerDTO newRequestPlayerDTO) {
        return playerService.createNewPlayer(newRequestPlayerDTO);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "List all players")
    public List<Player> getPlayers() {
        return playerService.getAllPlayers();
    }

    @RequestMapping(value = "/logged", method = RequestMethod.GET)
    @ApiOperation(value = "Get the logged player")
    public Player getLoggedPlayer() {
        return playerService.getLoggedUser().orElse(null);
    }

}
