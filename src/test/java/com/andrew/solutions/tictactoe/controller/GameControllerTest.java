package com.andrew.solutions.tictactoe.controller;

import com.andrew.solutions.tictactoe.TestConfig;
import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.service.GameService;
import com.andrew.solutions.tictactoe.service.PlayerService;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GameController.class, TestConfig.class, GameService.class})
@WebMvcTest
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/tictactoe/api/v1/game";

    private Game game;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        game = new Game(1L, new Player("user", "pass"));
        when(playerService.getLoggedUser()).thenReturn(Optional.of(new Player("user2", "pass")));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void createGame() throws Exception {
        when(gameService.createGame(any())).thenReturn(game);
        mockMvc.perform(post(baseUrl +"/create").with(csrf())
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void getGamesToJoin() throws Exception {
        given(gameService.getAllWaitingGames()).willReturn(Arrays.asList(
                new Game(2L, new Player("user2", "pass")),
                new Game(3L, new Player("user1", "pass"))));
        mockMvc.perform(get(baseUrl + "/join/list")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.[0].id").value(2))
                .andExpect(jsonPath("$.[0].currentPiece").value("X"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void getGames() throws Exception {
        given(gameService.getAllGames()).willReturn(Arrays.asList(
                new Game(2L, new Player("user2", "pass")),
                new Game(3L, new Player("user1", "pass"))));
        mockMvc.perform(get(baseUrl + "/list")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.[0].id").value(2))
                .andExpect(jsonPath("$.[0].currentPiece").value("X"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void joinGame() throws Exception {
        when(gameService.joinGame(any(),anyLong())).thenReturn(game);
        mockMvc.perform(post(baseUrl +"/join").with(csrf())
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content("{\"id\":0}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void getGameById() throws Exception {
        when(gameService.getGameById(anyLong())).thenReturn(Optional.of(game));
        mockMvc.perform(get(baseUrl + "/1")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.currentPiece").value("X"))
                .andReturn();
    }
}
