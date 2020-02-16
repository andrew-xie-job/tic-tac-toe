package com.andrew.solutions.tictactoe.controller;


import com.andrew.solutions.tictactoe.TestConfig;
import com.andrew.solutions.tictactoe.domain.Coordination;
import com.andrew.solutions.tictactoe.domain.Game;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.domain.enums.Column;
import com.andrew.solutions.tictactoe.domain.enums.GameStatus;
import com.andrew.solutions.tictactoe.domain.enums.Piece;
import com.andrew.solutions.tictactoe.domain.enums.Row;
import com.andrew.solutions.tictactoe.service.GameService;
import com.andrew.solutions.tictactoe.service.MoveService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MoveController.class, MoveService.class, TestConfig.class, GameService.class})
@WebMvcTest
public class MoveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/tictactoe/api/v1/move";

    @MockBean
    private GameService gameService;

    @MockBean
    private MoveService moveService;

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game(1L, new Player("user", "pass"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void shouldGetAllMoves() throws Exception {
        when(gameService.getGameById(anyLong())).thenReturn(Optional.of(game));
        given(moveService.getMovesInGame(game)).willReturn(Arrays.asList(
                new Coordination(Row.R1, Column.C1),
                new Coordination(Row.R3, Column.C2)));

        mockMvc.perform(get(baseUrl + "/list").sessionAttr("gameId", 1L)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.[0].row").value("R1"))
                .andExpect(jsonPath("$.[0].column").value("C1"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void shouldGetPlayerMoves() throws Exception {
        when(gameService.getGameById(anyLong())).thenReturn(Optional.of(game));
        given(moveService.getPlayerMoves(game, Piece.O)).willReturn(Arrays.asList(
                new Coordination(Row.R1, Column.C1),
                new Coordination(Row.R3, Column.C2)));

        mockMvc.perform(get(baseUrl + "/list/O").sessionAttr("gameId", 1L)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.[0].row").value("R1"))
                .andExpect(jsonPath("$.[0].column").value("C1"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void shouldGetGameStatus() throws Exception {
        when(gameService.getGameById(anyLong())).thenReturn(Optional.of(game));
        given(moveService.checkGameStatus(game)).willReturn(GameStatus.IN_PROGRESS);

        mockMvc.perform(get(baseUrl + "/status").sessionAttr("gameId", 1L)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.gameStatus").value("In-progress"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void shouldCreateMove() throws Exception {
        when(gameService.getGameById(anyLong())).thenReturn(Optional.of(game));

        mockMvc.perform(post(baseUrl +"/play").sessionAttr("gameId", 1L).with(csrf())
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content("{\"boardColumn\":0, \"boardRow\":0}"))
                .andExpect(status().isCreated());
    }


}
