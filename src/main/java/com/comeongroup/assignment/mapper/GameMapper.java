package com.comeongroup.assignment.mapper;

import com.comeongroup.assignment.data.dto.game.CreateGameDto;
import com.comeongroup.assignment.data.dto.game.GameDto;
import com.comeongroup.assignment.data.dto.game.UpdateGameDto;
import com.comeongroup.assignment.data.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link Game} and its DTOs.
 */
@Mapper(componentModel = "spring")
public interface GameMapper extends BaseMapper<CreateGameDto, UpdateGameDto, GameDto, Game> {

    @Mapping(target = "id", ignore = true)
    Game fromCreateDTO(CreateGameDto dto);

    Game fromUpdateDTO(UpdateGameDto dto);

    GameDto toDto(Game entity);
}