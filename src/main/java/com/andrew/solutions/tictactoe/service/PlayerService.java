package com.andrew.solutions.tictactoe.service;

import com.andrew.solutions.tictactoe.repository.PlayerRepository;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.dto.RequestPlayerDTO;
import com.andrew.solutions.tictactoe.security.ContextUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createNewPlayer(RequestPlayerDTO requestPlayerDTO) {
        Player newPlayer = new Player(requestPlayerDTO.getUserName(), passwordEncoder.encode(requestPlayerDTO.getPassword()));
        playerRepository.createPlayer(requestPlayerDTO.getUserName(), newPlayer);
        return newPlayer;
    }

    public Optional<Player> getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playerRepository.getPlayer(principal.getPlayer().getUserName());
    }

    public List<Player> getAllPlayers() {
        return  playerRepository.getAllPlayers();
    }

}
