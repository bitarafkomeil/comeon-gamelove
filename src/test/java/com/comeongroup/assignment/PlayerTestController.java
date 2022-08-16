package com.comeongroup.assignment;

import com.comeongroup.assignment.data.dto.game.CreateGameDto;
import com.comeongroup.assignment.data.dto.game.GameDto;
import com.comeongroup.assignment.data.dto.gamedata.LoveUnlovedDto;
import com.comeongroup.assignment.data.dto.player.CreatePlayerDto;
import com.comeongroup.assignment.data.dto.player.PlayerDto;
import com.comeongroup.assignment.data.dto.player.UpdatePlayerDto;
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
class PlayerTestController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static PlayerDto playerDto;
    private static PlayerDto deletePlayerDto;
    private static GameDto gameDto;


    @Test
    @Order(1)
    public void createPlayer() throws Exception {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setName("Test Player");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/player")
                .content(asJsonString(createPlayerDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        playerDto = objectMapper.readValue(contentAsString, PlayerDto.class);
    }

    @Test
    @Order(2)
    public void createPlayerForDelete() throws Exception {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setName("Delete Player");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/player")
                .content(asJsonString(createPlayerDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        deletePlayerDto = objectMapper.readValue(contentAsString, PlayerDto.class);
    }

    @Test
    @Order(3)
    public void updatePlayer() throws Exception {
        UpdatePlayerDto updatePlayerDto = new UpdatePlayerDto();
        updatePlayerDto.setId(playerDto.getId());
        updatePlayerDto.setName("Updated Test Player");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/player")
                .content(asJsonString(updatePlayerDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        playerDto = objectMapper.readValue(contentAsString, PlayerDto.class);
    }

    @Test
    @Order(4)
    public void getPlayer() throws Exception {
        mockMvc.perform(get("/api/player/{id}", playerDto.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void getAllPlayers() throws Exception {
        mockMvc.perform(get("/api/player/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    public void deletePlayer() throws Exception {
        mockMvc.perform(delete("/api/player/{id}", deletePlayerDto.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @Order(7)
    public void createTempGame() throws Exception {
        CreateGameDto createGameDto = new CreateGameDto();
        createGameDto.setName("Test Game ");

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
    @Order(8)
    public void LoveUnloved() throws Exception {
        LoveUnlovedDto loveUnlovedDto = new LoveUnlovedDto();
        loveUnlovedDto.setPlayerId(playerDto.getId());
        loveUnlovedDto.setGameId(gameDto.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/player/love-unloved")
                .content(asJsonString(loveUnlovedDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    /*@Test
    @Order(5)
    public void getAllNearestStations() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("companyId", stationDto.getCompanyId().toString());
        paramMap.add("latitude", String.valueOf(2d));
        paramMap.add("longitude", String.valueOf(3d));
        paramMap.add("radius", String.valueOf(1000d));
        mockMvc.perform(get("/api/stations/nearest")
                .params(paramMap))
                .andExpect(status().isOk())
                .andDo(print());
    }*/


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
