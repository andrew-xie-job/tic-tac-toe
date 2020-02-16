package com.andrew.solutions.tictactoe.service;

import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.exceptions.InvalidGameException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository = new GameRepository();
    public GameService () {
        gameRepository.clear();
    }

    public Game createGame(Player player) {
        return gameRepository.createGame(player);
    }

    public Game joinGame(Player player, long gameId) {
        Game game = getGameById(gameId)
                .orElseThrow(() -> new InvalidGameException("The GameId: " + gameId + " cannot be found"));
        game.joinGame(player);
        return game;
    }

    public Optional<Game> getGameById(long id) {
        return gameRepository.findGameById(id);
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public List<Game> getAllWaitingGames() {
        return gameRepository.getAllGames().stream()
                .filter(game -> game.getStatus() == GameStatus.WAITING)
                .collect(Collectors.toList());
    }

    private static class GameRepository {
        private static final Map<Long, Game> gameRepository = new ConcurrentHashMap<>();
        private static final AtomicLong sequence = new AtomicLong(1L);

        private static long generate() {
            return sequence.getAndIncrement();
        }

        public synchronized Game createGame(Player Player) {
            long id = generate();
            Game game = new Game(id, Player);
            gameRepository.put(id, game);
            return game;
        }

        public Optional<Game> findGameById(long id) {
            return Optional.ofNullable(gameRepository.get(id));
        }

        public List<Game> getAllGames() {
            return gameRepository.values().stream().collect(Collectors.toList());
        }

        public void clear() {
            gameRepository.clear();
        }
    }

}
