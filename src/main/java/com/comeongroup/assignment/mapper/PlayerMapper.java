package com.comeongroup.assignment.mapper;

import com.comeongroup.assignment.data.dto.player.CreatePlayerDto;
import com.comeongroup.assignment.data.dto.player.PlayerDto;
import com.comeongroup.assignment.data.dto.player.UpdatePlayerDto;
import com.comeongroup.assignment.data.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link Player} and its DTOs.
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper extends BaseMapper<CreatePlayerDto, UpdatePlayerDto, PlayerDto, Player> {

    @Mapping(target = "id", ignore = true)
    Player fromCreateDTO(CreatePlayerDto dto);

    Player fromUpdateDTO(UpdatePlayerDto dto);

    PlayerDto toDto(Player entity);
}