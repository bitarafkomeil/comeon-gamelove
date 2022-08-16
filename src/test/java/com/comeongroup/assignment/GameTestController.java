package com.comeongroup.assignment;

import com.comeongroup.assignment.data.dto.game.CreateGameDto;
import com.comeongroup.assignment.data.dto.game.GameDto;
import com.comeongroup.assignment.data.dto.game.UpdateGameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameTestController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static GameDto gameDto;
    private static GameDto deleteGameDto;


    @Test
    @Order(1)
    public void createGame() throws Exception {
        CreateGameDto createGameDto = new CreateGameDto();
        createGameDto.setName("Test Game 1");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/game")
                .content(asJsonString(createGameDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        gameDto = objectMapper.readValue(contentAsString, GameDto.class);
    }

    @Test
    @Order(2)
    public void createGameForDelete() throws Exception {
        CreateGameDto createGameDto = new CreateGameDto();
        createGameDto.setName("Test Game 2");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/game")
                .content(asJsonString(createGameDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        deleteGameDto = objectMapper.readValue(contentAsString, GameDto.class);
    }

    @Test
    @Order(3)
    public void updateGame() throws Exception {
        UpdateGameDto updateGameDto = new UpdateGameDto();
        updateGameDto.setId(gameDto.getId());
        updateGameDto.setName("Updated Test Game");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/game")
                .content(asJsonString(updateGameDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        gameDto = objectMapper.readValue(contentAsString, GameDto.class);
    }

    @Test
    @Order(4)
    public void getGame() throws Exception {
        mockMvc.perform(get("/api/game/{id}", gameDto.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void getAllGames() throws Exception {
        mockMvc.perform(get("/api/game/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    public void deleteGame() throws Exception {
        mockMvc.perform(delete("/api/game/{id}", deleteGameDto.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @Order(7)
    public void getMostLoved() throws Exception {
        mockMvc.perform(get("/api/game/data/top"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(8)
    public void getAllGameData() throws Exception {
        mockMvc.perform(get("/api/game/data/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
