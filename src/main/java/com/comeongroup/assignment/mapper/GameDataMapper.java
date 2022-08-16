package com.comeongroup.assignment.mapper;

import com.comeongroup.assignment.data.dto.gamedata.GameDataDto;
import com.comeongroup.assignment.data.model.GameData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link GameData} and its DTOs.
 */
@Mapper(componentModel = "spring")
public interface GameDataMapper {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "player.name", target = "playerName")
    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.name", target = "gameName")
    GameDataDto toDto(GameData entity);
}