package com.andrew.solutions.tictactoe.controller;

import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.dto.RequestPlayerDTO;
import com.andrew.solutions.tictactoe.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tictactoe/api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Player createAccount(@RequestBody RequestPlayerDTO newRequestPlayerDTO) {
        return playerService.createNewPlayer(newRequestPlayerDTO);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Player> getPlayers() {
        return playerService.getAllPlayers();
    }

    @RequestMapping(value = "/logged", method = RequestMethod.GET)
    public Player getLoggedPlayer() {
        return playerService.getLoggedUser().orElse(null);
    }

}
