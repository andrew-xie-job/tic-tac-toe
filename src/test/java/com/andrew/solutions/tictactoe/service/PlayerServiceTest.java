package com.andrew.solutions.tictactoe.service;

import com.andrew.solutions.tictactoe.repository.PlayerRepository;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.dto.RequestPlayerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerServiceTest {
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private PlayerService playerService;
    private PlayerRepository playerRepository;
    private RequestPlayerDTO requestPlayerDTO;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepository();
        playerService = new PlayerService(playerRepository);
        requestPlayerDTO = new RequestPlayerDTO(USER, PASSWORD);
    }

    @Test
    public void willCreate_NewPlayer() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Player player = playerService.createNewPlayer(requestPlayerDTO);
        assertThat(player.getUserName()).isEqualTo(USER);
        assertThat(passwordEncoder.matches(PASSWORD, player.getPassword())).isTrue();
    }
}
