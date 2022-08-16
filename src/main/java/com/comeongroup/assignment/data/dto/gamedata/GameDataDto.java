package com.comeongroup.assignment.data.dto.gamedata;


import lombok.Data;

@Data
public class GameDataDto {
    private Long id;
    private Long playerId;
    private String playerName;
    private Long gameId;
    private String gameName;
}