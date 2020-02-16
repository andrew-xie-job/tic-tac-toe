package com.andrew.solutions.tictactoe;

import com.andrew.solutions.tictactoe.dto.RequestPlayerDTO;
import com.andrew.solutions.tictactoe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicTacToeApplication {

    @Autowired
    private PlayerService playerService;

    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerService playerService) {
        return (args) -> {
            playerService.createNewPlayer(new RequestPlayerDTO("xPlayer", "xPlayer"));
            playerService.createNewPlayer(new RequestPlayerDTO("oPlayer", "oPlayer"));
        };
    }

}
