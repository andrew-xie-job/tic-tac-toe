package com.andrew.solutions.tictactoe.Repository;

import com.andrew.solutions.tictactoe.domain.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerRepository {
    private final Map<String, Player> playerRepository = new HashMap<>();

    public Optional<Player> getPlayer(String userName) {
        return Optional.ofNullable(playerRepository.get(userName));
    }

    public void createPlayer(String userName, Player player) {
        playerRepository.put(userName, player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.values().stream().collect(Collectors.toList());
    }
}
