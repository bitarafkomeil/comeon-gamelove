package com.comeongroup.assignment.service;

import com.comeongroup.assignment.data.dto.game.CreateGameDto;
import com.comeongroup.assignment.data.dto.game.GameDto;
import com.comeongroup.assignment.data.dto.game.UpdateGameDto;
import com.comeongroup.assignment.data.dto.gamedata.GameDataDto;
import com.comeongroup.assignment.data.dto.gamedata.MostLovedDto;
import com.comeongroup.assignment.data.model.Game;
import com.comeongroup.assignment.data.repository.GameDataRepository;
import com.comeongroup.assignment.data.repository.GameRepository;
import com.comeongroup.assignment.exception.BadRequestException;
import com.comeongroup.assignment.mapper.GameDataMapper;
import com.comeongroup.assignment.mapper.GameMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService extends CrudService<Game, GameRepository,
        CreateGameDto, UpdateGameDto, GameDto, GameMapper> {
    private final GameDataRepository gameDataRepository;
    private final GameDataMapper gameDataMapper;

    public GameService(GameRepository repository, GameMapper mapper, GameDataRepository gameDataRepository, GameDataMapper gameDataMapper) {
        super(repository, mapper);
        this.gameDataRepository = gameDataRepository;
        this.gameDataMapper = gameDataMapper;
    }

    @Override
    protected void preCreate(Game entity) {
        if (repository.existsByName(entity.getName()))
            throw new BadRequestException("Duplicate Game Name");
    }

    @Override
    protected void preDelete(Long id) {
        if (gameDataRepository.existsByGameId(id))
            throw new BadRequestException("Game Has GameData,Delete Game Data Before Deleting The Game ");
    }

    public List<MostLovedDto> getMostLovedGames(Integer count) {
        return gameDataRepository.getMostTop(count);
    }

    public Page<GameDataDto> getAllGameData(Pageable pageable, Optional<Long> playerId) {
        return gameDataRepository.getAllByPlayerIdAndPageable(pageable, playerId).map(gameDataMapper::toDto);
    }
}