package com.andrew.solutions.tictactoe.controller;

import com.andrew.solutions.tictactoe.TestConfig;
import com.andrew.solutions.tictactoe.domain.Player;
import com.andrew.solutions.tictactoe.repository.PlayerRepository;
import com.andrew.solutions.tictactoe.service.PlayerService;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PlayerController.class, PlayerService.class, TestConfig.class})
@WebMvcTest
public class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerRepository repository;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void shouldGetAllPlayers() throws Exception {

        given(repository.getAllPlayers()).willReturn(
                Arrays.asList(
                        new Player("Baggins", "ring bearer"),
                        new Player("Bilbo", "Baggins")));

        mockMvc.perform(get("/tictactoe/api/v1/player/list").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.[0].userName").value("Baggins"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void shouldCreatePlayer() throws Exception {

        doNothing().when(repository).createPlayer("user", new Player("user", "password"));

        mockMvc.perform(post("/tictactoe/api/v1/player/create").with(csrf())
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content("{\"userName\":\"user\", \"password\":\"password\"}"))
                .andExpect(status().isCreated());
    }


}
